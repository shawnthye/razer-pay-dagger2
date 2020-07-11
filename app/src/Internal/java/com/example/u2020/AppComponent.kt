package com.example.u2020

import com.example.u2020.data.DataModule
import com.example.u2020.data.InternalDataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, InternalDataModule::class])
interface AppComponent : AppGraph {
}