package com.example.u2020.data.api

import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

class ApiModule {
    @Provides @Singleton fun provideRestAdapter(
        endpoint: Endpoint?,
        client: Client?,
        headers: ApiHeaders?
    ): RestAdapter? {
        retrofit2
        return Builder() //
            .setClient(client) //
            .setEndpoint(endpoint) //
            .setRequestInterceptor(headers) //
            .build()
    }
}