package com.example.u2020.ui.debug

import android.content.Context
import android.widget.ArrayAdapter
import com.example.u2020.R
import com.example.u2020.data.ApiServers
import com.example.u2020.data.prefs.StringPreference

internal class NetworkServerAdapter(
    context: Context,
    private val customServerURL: StringPreference
) : ArrayAdapter<String>(
    context,
    R.layout.debug_dropdown_menu_popup_item,
    emptyArray()
) {

    // for caching, so we dun query SharedPreference every time :)
    private var customURL: String? = customServerURL.get()

    override fun getCount(): Int {
        return SERVERS.size
    }

    override fun getItem(position: Int): String {
        return getItem(SERVERS[position])
    }

    fun getItem(server: ApiServers): String {
        return if (server != ApiServers.CUSTOM) {
            return server.toString()
        } else {
            customURL ?: server.toString()
        }
    }

    fun getServer(position: Int): ApiServers {
        return SERVERS[position]
    }

    override fun getItemId(position: Int): Long {
        return SERVERS[position].ordinal.toLong()
    }

    override fun notifyDataSetChanged() {
        customURL = customServerURL.get()
        super.notifyDataSetChanged()
    }

//    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = super.getDropDownView(position, convertView, parent)
//        return bindView(getItem(position), view)
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = super.getView(position, convertView, parent)
//        return bindView(getItem(position), view)
//    }
//
//    private fun bindView(server: ApiServers, view: View): View {
//        val textView = view as TextView
//        val text = if (server != ApiServers.CUSTOM) {
//            server.toString()
//        } else {
//            "server.toString()"
//        }
//
//        textView.text = text
//        return view
//    }

    private companion object {
        val SERVERS = ApiServers.values()
    }

}