package com.example.u2020.data.prefs

import android.content.SharedPreferences

class IntPreference(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: Int = 0
) {

    fun get(): Int {
        return preferences.getInt(key, defaultValue)
    }

    val isSet: Boolean
        get() = preferences.contains(key)

    fun set(value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun delete() {
        preferences.edit().remove(key).apply()
    }

}