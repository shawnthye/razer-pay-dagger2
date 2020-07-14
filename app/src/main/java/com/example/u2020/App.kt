package com.example.u2020

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}