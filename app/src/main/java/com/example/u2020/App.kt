package com.example.u2020

import androidx.appcompat.app.AppCompatDelegate
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> {
        return buildComponent()
    }

    fun buildComponent(): AndroidInjector<App> {
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}