/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

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