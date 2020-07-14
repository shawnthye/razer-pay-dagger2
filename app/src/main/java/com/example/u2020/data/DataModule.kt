package com.example.u2020.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.u2020.BuildConfig
import com.example.u2020.data.api.ApiModule
import com.example.u2020.data.database.WordRoomDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class DataModule {

    companion object {
        const val SHARED_PREFERENCES_NAME = "RazerPay"
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WordRoomDatabase {
        return Room.inMemoryDatabaseBuilder(
            app.applicationContext,
            WordRoomDatabase::class.java
        ).build()
    }

    @Provides @Singleton fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                Level.BASIC
            } else {
                Level.NONE
            }
        )
    }

    @Provides @Singleton fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }
}