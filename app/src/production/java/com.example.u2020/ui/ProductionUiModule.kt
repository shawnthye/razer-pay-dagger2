package com.example.u2020.ui

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductionUiModule : UiModule {

    @Provides
    @Singleton
    override fun provideAppContainer(): AppContainer {
        return super.provideAppContainer()
    }
}