package com.example.u2020.data.api

import com.example.u2020.data.ApiCustomServerURL
import com.example.u2020.data.ApiServer
import com.example.u2020.data.ApiServers
import com.example.u2020.data.prefs.IntPreference
import com.example.u2020.data.prefs.StringPreference
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class InternalApiModule {

    @Provides @Singleton fun provideHttpUrl(
        @ApiServer apiServer: IntPreference,
        @ApiCustomServerURL customServerURL: StringPreference
    ): HttpUrl {

        val apiServers = ApiServers.from(apiServer.get())
        return if (apiServers != ApiServers.CUSTOM) {
            apiServers.url?.let { HttpUrl.parse(it) }
        } else {
            customServerURL.get()?.let { HttpUrl.parse(it) }
        } ?: throw IllegalArgumentException("Missing Wallet Api URL")
    }
}