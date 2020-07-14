package com.example.u2020.data.api

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class ProductionApiModule {

    @Provides @Singleton fun provideHttpUrl(
    ): HttpUrl {
        return ApiModule.PRODUCTION_WALLET_API_URL.toHttpUrlOrNull()
            ?: throw IllegalArgumentException("Missing Wallet Api URL")
    }
}