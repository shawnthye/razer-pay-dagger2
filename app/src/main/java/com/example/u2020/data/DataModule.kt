package com.example.u2020.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.u2020.data.database.WordRoomDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("RazerPay", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WordRoomDatabase {
        return Room.inMemoryDatabaseBuilder(
            app.applicationContext,
            WordRoomDatabase::class.java
        ).build()
    }

    companion object {

//        const val DISK_CACHE_SIZE = 50 * 1024 * 1024 // 50MB

        fun createOkHttpClient(networkInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient
                .Builder()
                .addNetworkInterceptor(networkInterceptor)
                .build()
        }
    }
}