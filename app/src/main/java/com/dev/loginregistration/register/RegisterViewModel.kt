package com.dev.loginregistration.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.loginregistration.database.UserEntity
import com.dev.loginregistration.database.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class RegisterViewModel(private val repository: UserRepository, application: Application) :
    AndroidViewModel(application), Observable {

    init {
        Log.i("Register", "init")
    }

    @Bindable
    val inputFullName = MutableLiveData<String>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<String>()

    val errotoastUsername: LiveData<String>
        get() = _errorToastUsername

    private val _errorToastPassword = MutableLiveData<String>()

    val errotoastPassword: LiveData<String>
        get() = _errorToastPassword


    fun registerUser() {
        val digit = Pattern.compile(".*\\d.*")
        val upperCase = Pattern.compile(".*[A-Z].*")
        val lowerCase = Pattern.compile(".*[a-z].*")

        if (inputFullName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else if (inputUsername.value!!.length < 5) {
            _errorToastUsername.value = "Username must be greater than 5 characters."
        } else if (inputPassword.value!!.length < 4) {
            _errorToastPassword.value = "Password must be greater than 4 characters."
        } else if (!digit.matcher(inputPassword.value!!).matches()) {
            _errorToastPassword.value = "Password must contain 1 digit."
        } else if (!upperCase.matcher(inputPassword.value!!).matches()) {
            _errorToastPassword.value = "Password must contain 1 uppercase letter."
        } else if (!lowerCase.matcher(inputPassword.value!!).matches()) {
            _errorToastPassword.value = "Password must contain 1 lowercase letter."
        } else {
            uiScope.launch {
//            withContext(Dispatchers.IO) {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    _errorToastUsername.value = "Username already exists."
                } else {
                    val fullName = inputFullName.value!!
                    val username = inputUsername.value!!
                    val password = inputPassword.value!!
                    insert(UserEntity(0, fullName, username, password))
                    inputFullName.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    _navigateto.value = true
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
    }

    fun hideToast() {
        _errorToast.value = false
    }

    fun hideToastUserName() {
        _errorToastUsername.value = ""
    }

    fun hideToastPassword() {
        _errorToastPassword.value = ""
    }

    private fun insert(user: UserEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}





