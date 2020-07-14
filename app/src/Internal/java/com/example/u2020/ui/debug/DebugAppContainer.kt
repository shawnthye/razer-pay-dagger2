package com.example.u2020.ui.debug

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.u2020.App
import com.example.u2020.BuildConfig
import com.example.u2020.R
import com.example.u2020.data.ApiCustomServerURL
import com.example.u2020.data.ApiServer
import com.example.u2020.data.ApiServers
import com.example.u2020.data.SeenDebugDrawer
import com.example.u2020.data.prefs.BooleanPreference
import com.example.u2020.data.prefs.IntPreference
import com.example.u2020.data.prefs.StringPreference
import com.example.u2020.ui.AppContainer
import com.example.u2020.ui.activities.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.internal.debug_activity_root.*
import kotlinx.android.synthetic.internal.debug_drawer.*
import kotlinx.android.synthetic.internal.debug_drawer.view.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugAppContainer @Inject constructor(
    private val app: App,
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    @ApiServer private val apiServer: IntPreference,
    @ApiCustomServerURL private val apiCustomServerURL: StringPreference,
    @SeenDebugDrawer private val seenDebugDrawer: BooleanPreference
) : AppContainer {

    private lateinit var networkServerAdapter: NetworkServerAdapter

    override fun getContainer(activity: Activity): ViewGroup {
        activity.setContentView(R.layout.debug_activity_root)

        val drawerContent = activity.debug_drawer_content;
        setupNetworkServers(drawerContent)
        activity.editServerURL.setOnClickListener {
            askCustomServerUrlIfBlank(drawerContent, ApiServers.CUSTOM, true)
        }

        val seenDebugDrawer = seenDebugDrawer.value
        val drawer = activity.debug_drawer_layout

        drawer.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                this@DebugAppContainer.seenDebugDrawer.value = true
            }

            override fun onDrawerOpened(drawerView: View) {
                /* The back button won't work without this */
                drawer.requestFocus()
            }
        })
        if (!seenDebugDrawer) {
            drawer.postDelayed({
                drawer.openDrawer(GravityCompat.END)
                val snackBar = Snackbar.make(
                    drawer,
                    R.string.debug_drawer_welcome,
                    Snackbar.LENGTH_LONG
                )
                snackBar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
                snackBar.show()
            }, 1000)
        }

        setupNetworkLoggingLevels(drawerContent)
        setupBuildInformation(drawerContent)
        setupDeviceInformation(drawerContent)
        return activity.app_content
    }

    private fun setupNetworkServers(view: ViewGroup) {
        val servers = view.networkServers
        networkServerAdapter = NetworkServerAdapter(servers.context, apiCustomServerURL)

        servers.keyListener = null
        servers.setAdapter(networkServerAdapter)
        servers.setOnItemClickListener { _, _, position, _ ->
            networkServerAdapter.getServer(position).also {
                val prevServer = ApiServers.from(apiServer.get())
                onServerSelected(view, prevServer, it)
            }
        }

        val selected = ApiServers.from(apiServer.get())
        if (selected != ApiServers.CUSTOM) {
            view.editServerURL.visibility = GONE
            servers.setText(selected.toString(), false)
        } else {
            view.editServerURL.visibility = VISIBLE
            servers.setText(networkServerAdapter.getItem(ApiServers.CUSTOM), false)
        }
    }

    private fun onServerSelected(
        view: ViewGroup,
        prevServer: ApiServers,
        selectedServer: ApiServers
    ) {

        if (selectedServer == prevServer) {
            return
        }

        if (selectedServer != ApiServers.CUSTOM) {
            apiServer.set(selectedServer.ordinal)
            relaunch()
            view.editServerURL.visibility = GONE
            return
        }

        askCustomServerUrlIfBlank(view, prevServer)
    }

    private fun askCustomServerUrlIfBlank(
        view: ViewGroup,
        prevServer: ApiServers,
        force: Boolean = false
    ) {

        val networkServers = view.networkServers
        val customServerURL = apiCustomServerURL.get();
        if (customServerURL.isNullOrBlank().not() && !force) {
            apiServer.set(ApiServers.CUSTOM.ordinal)
            view.editServerURL.visibility = VISIBLE
            relaunch()
            return
        }

        val context = view.context

        val dialog = AlertDialog.Builder(context)
            .setTitle("Set custom server")
            .setView(R.layout.debug_custom_server_url_dialog)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton("Cancel") { _, _ ->
                // set to previous selection
                networkServers.setText(networkServerAdapter.getItem(prevServer), false)
            }
            .show()

        val urlEditText = dialog.findViewById<EditText>(R.id.url)
        customServerURL?.also {
            urlEditText?.setText(it)
        }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            //TODO: check url format
            urlEditText
                ?.text
                ?.toString()
                ?.takeIf { it.trim().isNotBlank() && URLUtil.isValidUrl(it) }
                ?.also {
                    apiServer.set(ApiServers.CUSTOM.ordinal)
                    apiCustomServerURL.set(it)
                    dialog.dismiss()
                    relaunch()
                } ?: {
                Toast.makeText(context, "Invalid url", 0).show()
            }()
        }
    }

    private fun setupNetworkLoggingLevels(view: View) {
        val levels = Level.values();
        val adapter = ArrayAdapter<Level>(
            view.context,
            R.layout.debug_dropdown_menu_popup_item,
            levels
        )
        val textView = view.networkLoggingLevels
        textView.setAdapter(adapter)
        textView.setOnItemClickListener { _, _, position, _ ->
            adapter.getItem(position)?.also {
                httpLoggingInterceptor.setLevel(it)
            }
        }
        textView.keyListener = null
        textView.setText(adapter.getItem(httpLoggingInterceptor.level.ordinal).toString(), false)
    }

    private fun setupBuildInformation(view: ViewGroup) {
        view.internal_build_name.text = BuildConfig.VERSION_NAME
        view.internal_build_code.text = BuildConfig.VERSION_CODE.toString()
    }

    private fun setupDeviceInformation(view: ViewGroup) {
        val resources = view.resources
        val displayMetrics = resources.displayMetrics
        view.internal_device_make.text = Build.MANUFACTURER
        view.internal_device_model.text = Build.MODEL

        view.internal_device_resolution.text = resources.getString(
            R.string.debug_drawer_header_device_resolution_template,
            displayMetrics.heightPixels,
            displayMetrics.widthPixels
        )

        view.internal_device_density.text = resources.getString(
            R.string.debug_drawer_header_device_density_template,
            displayMetrics.densityDpi,
            displayMetrics.densityBucket
        )

        view.internal_device_release.text = Build.VERSION.RELEASE
        view.internal_device_api.text = Build.VERSION.SDK_INT.toString()
    }

    private fun relaunch() {
        val newApp = Intent(app, MainActivity::class.java)
        Intent.makeRestartActivityTask(newApp.component)
        app.startActivity(Intent.makeRestartActivityTask(newApp.component))
        app.buildComponent().inject(app)
    }

    private val DisplayMetrics.densityBucket: String
        get() {
            return when (densityDpi) {
                DisplayMetrics.DENSITY_LOW -> "ldpi"
                DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
                DisplayMetrics.DENSITY_HIGH -> "hdpi"
                DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
                DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
                DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
                DisplayMetrics.DENSITY_TV -> "tvdpi"
                else -> "unknown"
            }
        }
}