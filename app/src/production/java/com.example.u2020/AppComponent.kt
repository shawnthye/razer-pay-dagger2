package com.example.u2020

import com.example.u2020.data.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent : AppGraph {
}