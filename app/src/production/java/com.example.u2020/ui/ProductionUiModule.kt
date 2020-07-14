package com.example.u2020.ui

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [UiModule::class])
class ProductionUiModule {

    @Provides
    @Singleton
    fun provideAppContainer(): AppContainer {
        return AppContainer.DEFAULT
    }
}