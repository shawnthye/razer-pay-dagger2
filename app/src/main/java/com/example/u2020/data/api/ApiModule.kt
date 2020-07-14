package com.example.u2020.data.api

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides @Singleton fun provideRetrofit(client: OkHttpClient, httpUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(httpUrl)
            .build()
    }

    @Provides @Singleton fun provideWalletApi(retrofit: Retrofit): WalletApi {
        return retrofit.create(WalletApi::class.java)
    }

    companion object {
        const val PRODUCTION_WALLET_API_URL = "https://api.pay.razer.com"
    }
}