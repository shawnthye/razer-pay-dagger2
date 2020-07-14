package com.example.u2020.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.u2020.data.api.InternalApiModule
import com.example.u2020.data.prefs.BooleanPreference
import com.example.u2020.data.prefs.IntPreference
import com.example.u2020.data.prefs.StringPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule::class, InternalApiModule::class])
class InternalDataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application,
        @ApiServer apiServer: IntPreference
    ): SharedPreferences {
        return app.getSharedPreferences(
            "${DataModule.SHARED_PREFERENCES_NAME}_${ApiServers.from(apiServer.get()).name}",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    @DebugSharedPreferences
    fun provideDebugSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("debug_razerpay_development_settings", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @ApiServer
    fun provideServerPreference(@DebugSharedPreferences preferences: SharedPreferences): IntPreference {
        return IntPreference(preferences, "debug_server", ApiServers.UAT.ordinal)
    }

    @Provides
    @Singleton
    @ApiCustomServerURL
    fun provideCustomServerURLPreference(@DebugSharedPreferences preferences: SharedPreferences): StringPreference {
        return StringPreference(preferences, "debug_custom_server")
    }

    @Provides
    @Singleton
    @SeenDebugDrawer
    fun provideSeenDebugDrawer(@DebugSharedPreferences preferences: SharedPreferences): BooleanPreference {
        return BooleanPreference(preferences, "debug_seen_debug_drawer")
    }
}