package com.example.u2020

import android.app.Application


class App : Application() {

    // Reference to the application graph that is used across the whole app
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun component(): AppComponent {
        return component
    }
}