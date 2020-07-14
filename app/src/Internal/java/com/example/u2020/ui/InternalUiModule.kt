package com.example.u2020.ui

import com.example.u2020.ui.debug.DebugAppContainer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [UiModule::class])
class InternalUiModule {

    @Provides @Singleton fun provideAppContainer(debugAppContainer: DebugAppContainer): AppContainer {
        return debugAppContainer
    }
}