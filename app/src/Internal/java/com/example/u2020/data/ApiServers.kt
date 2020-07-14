package com.example.u2020.data

internal enum class ApiServers(private val description: String, val url: String?) {
    PRODUCTION(
        "Production",
        "https://api.pay.razer.com"
    ),
    UAT(
        "UAT",
        "https://api-uat.pay.razer.com"
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