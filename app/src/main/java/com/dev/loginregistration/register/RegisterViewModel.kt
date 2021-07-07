package com.dev.loginregistration.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.dev.loginregistration.database.UserEntity
import com.dev.loginregistration.database.UserRepository
import kotlinx.coroutines.*


class RegisterViewModel(private val repository: UserRepository, application: Application) :
    AndroidViewModel(application), Observable {

    init {
        Log.i("Register", "init")
    }


    private var userdata: String? = null

    var userDetailsLiveData = MutableLiveData<Array<UserEntity>>()

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

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun registerUser() {
        if (inputFullName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
//            withContext(Dispatchers.IO) {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    _errorToastUsername.value = true
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

    fun showToast() {
        _errorToast.value = false
    }

    fun showToastUserName() {
        _errorToast.value = false
    }

    private fun insert(user: UserEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}





