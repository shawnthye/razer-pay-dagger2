package com.example.u2020.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WalletApi {

    @GET("/v3/config/getLookup")
    fun getLookup(@Query("type") type: String?): Call<ResponseBody>
}