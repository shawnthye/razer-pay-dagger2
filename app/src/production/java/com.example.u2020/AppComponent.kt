package com.example.u2020

import com.example.u2020.data.ProductionDataModule
import com.example.u2020.ui.ProductionUiModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ProductionUiModule::class,
        ProductionDataModule::class
    ]
)
interface AppComponent : AppGraph {
}