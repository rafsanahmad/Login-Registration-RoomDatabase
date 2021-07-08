package com.dev.loginregistration.utils

import android.content.Context
import android.content.SharedPreferences


class Prefs(context: Context) {
    var REGISTER_STATUS = "register_status"
    private val preferences: SharedPreferences = context.getSharedPreferences(REGISTER_STATUS, 0)

    var registerStatus: Boolean
        get() = preferences.getBoolean(REGISTER_STATUS, false)
        set(value) = preferences.edit().putBoolean(REGISTER_STATUS, value).apply()
}