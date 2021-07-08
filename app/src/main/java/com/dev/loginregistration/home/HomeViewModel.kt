package com.dev.loginregistration.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.loginregistration.prefs

class HomeViewModel : ViewModel() {

    private val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    fun logoutUser() {
        prefs.registerStatus = false
        _navigateToLogin.value = true
    }

    fun doneNavigateToLogin() {
        _navigateToLogin.value = false
    }
}