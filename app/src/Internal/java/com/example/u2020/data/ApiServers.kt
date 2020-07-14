package com.example.u2020.data

import com.example.u2020.data.api.ApiModule

enum class ApiServers(private val description: String, val url: String?) {
    PRODUCTION(
        description = "Production",
        url = ApiModule.PRODUCTION_WALLET_API_URL
    ),
    UAT(
        description = "UAT",
        url = "https://api-uat.pay.razer.com"
    ),
    CUSTOM("Custom", null);

    override fun toString(): String {
        return description
    }

    internal companion object {
        fun from(ordinal: Int): ApiServers {
            val pos = values().size - 1
            if (ordinal >= 0 || ordinal <= pos) {
                return values()[ordinal]
            }
            return CUSTOM
        }
    }
}