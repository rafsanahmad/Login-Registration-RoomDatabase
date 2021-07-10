/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val foregroundMilliSeconds = 31000L
    val backgroundMilliSeconds = 11000L

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    fun logoutUser() {
        //prefs.registerStatus = false
        _navigateToLogin.value = true
    }

    fun doneNavigateToLogin() {
        _navigateToLogin.value = false
    }
}