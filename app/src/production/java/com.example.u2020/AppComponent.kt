package com.example.u2020

import com.example.u2020.ui.ProductionUiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ProductionUiModule::class])
interface AppComponent : AppGraph {
}