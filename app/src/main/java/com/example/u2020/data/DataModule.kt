package com.example.u2020.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.u2020.data.database.WordRoomDatabase
import dagger.Module
import dagger.Provides
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
}