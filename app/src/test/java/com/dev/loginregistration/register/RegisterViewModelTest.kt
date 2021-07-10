/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration.register

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.dev.loginregistration.MainApplication
import com.dev.loginregistration.database.UserRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var errortoast: LiveData<Boolean>
    private lateinit var errortoastUsername: LiveData<Boolean>
    private lateinit var errortoastPassword: LiveData<Boolean>
    var application: MainApplication? = null

    @Mock
    private lateinit var repo: UserRepository

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        application = mock(MainApplication::class.java)
        viewModel = RegisterViewModel(repo, Application())
    }

    @Test
    fun testRegisterUserWithEmptyUserName() {
        viewModel.inputFullName.postValue("Abdul karim")
        viewModel.inputUsername.postValue("")
        viewModel.inputPassword.postValue("1245")
        viewModel._errorToast.observeForever {}
        viewModel.registerUser()
        val value = viewModel.errortoastUsername.value
        assert(value.equals("Username must be greater than 5 characters."))
    }

    @Test
    fun testRegisterUserWithEmptyPassword() {
        viewModel.inputFullName.postValue("Abdul karim")
        viewModel.inputUsername.postValue("Karim")
        viewModel.inputPassword.postValue("")
        viewModel._errorToast.observeForever {}
        viewModel.registerUser()
        val value = viewModel.errortoastPassword.value
        assert(value.equals("Password must be greater than 4 characters."))
    }

    @Test
    fun testRegisterUserWithNoDigitPassword() {
        viewModel.inputFullName.postValue("Abdul karim")
        viewModel.inputUsername.postValue("Karim")
        viewModel.inputPassword.postValue("Aaaaaa")
        viewModel._errorToast.observeForever {}
        viewModel.registerUser()
        val value = viewModel.errortoastPassword.value
        assert(value.equals("Password must contain 1 digit."))
    }

    @Test
    fun testRegisterUserWithNoUppercasePassword() {
        viewModel.inputFullName.postValue("Abdul karim")
        viewModel.inputUsername.postValue("Karim")
        viewModel.inputPassword.postValue("aaaaa1")
        viewModel._errorToast.observeForever {}
        viewModel.registerUser()
        val value = viewModel.errortoastPassword.value
        assert(value.equals("Password must contain 1 uppercase letter."))
    }

    @Test
    fun testRegisterUserWithNoLowercasePassword() {
        viewModel.inputFullName.postValue("Abdul karim")
        viewModel.inputUsername.postValue("Karim")
        viewModel.inputPassword.postValue("AAAA1")
        viewModel._errorToast.observeForever {}
        viewModel.registerUser()
        val value = viewModel.errortoastPassword.value
        assert(value.equals("Password must contain 1 lowercase letter."))
    }
}