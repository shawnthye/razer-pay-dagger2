package com.example.u2020

import com.example.u2020.data.InternalDataModule
import com.example.u2020.ui.InternalUiModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        InternalUiModule::class,
        InternalDataModule::class
    ]
)
interface AppComponent : AppGraph