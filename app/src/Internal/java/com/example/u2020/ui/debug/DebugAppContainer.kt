package com.example.u2020.ui.debug

import android.app.Activity
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
import com.example.u2020.R
import com.example.u2020.data.ApiCustomServerURL
import com.example.u2020.data.ApiServer
import com.example.u2020.data.ApiServers
import com.example.u2020.data.SeenDebugDrawer
import com.example.u2020.data.prefs.BooleanPreference
import com.example.u2020.data.prefs.IntPreference
import com.example.u2020.data.prefs.StringPreference
import com.example.u2020.ui.AppContainer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.internal.debug_activity_root.*
import kotlinx.android.synthetic.internal.debug_drawer.*
import kotlinx.android.synthetic.internal.debug_drawer.view.*
import okhttp3.logging.HttpLoggingInterceptor.Level
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugAppContainer @Inject constructor(
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
                    networkServers.setText(it, false)
                    apiServer.set(ApiServers.CUSTOM.ordinal)
                    apiCustomServerURL.set(it)
                    networkServerAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                    view.editServerURL.visibility = VISIBLE
                } ?: {
                // failed to set server, revert back to previous selection
                networkServers.setText(prevServer.toString(), false)
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
        textView.keyListener = null
        textView.setText(adapter.getItem(Level.BASIC.ordinal).toString(), false)
    }
}