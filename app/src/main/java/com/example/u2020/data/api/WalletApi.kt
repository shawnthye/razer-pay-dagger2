package com.example.u2020.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WalletApi {

    @GET("/v3/config/getLookup")
    fun getLookup(@Query("type") type: String?): String
}