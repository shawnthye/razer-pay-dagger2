package com.example.u2020.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.u2020.data.api.ProductionApiModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule::class, ProductionApiModule::class])
class ProductionDataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(DataModule.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}