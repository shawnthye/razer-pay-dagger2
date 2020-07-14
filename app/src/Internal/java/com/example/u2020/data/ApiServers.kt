package com.example.u2020.data

internal enum class ApiServers(private val description: String, val url: String?) {
    PRODUCTION(
        "Production",
        "ApiModule.PRODUCTION_API_URL"
    ),
    UAT(
        "UAT",
        "ApiModule.UAT_API_URL"
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