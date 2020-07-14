package com.example.u2020.ui

import android.app.Activity
import android.view.ViewGroup
import com.example.u2020.R

interface AppContainer {
    /** The root [android.view.ViewGroup] into which the activity should place its contents.  */
    fun getContainer(activity: Activity): ViewGroup

    companion object {
        /** An [AppContainer] which returns the normal activity content view.  */
        @Suppress("unused")
        val DEFAULT: AppContainer = object : AppContainer {
            override fun getContainer(activity: Activity): ViewGroup {
                return activity.findViewById(R.id.content)
            }
        }
    }
}