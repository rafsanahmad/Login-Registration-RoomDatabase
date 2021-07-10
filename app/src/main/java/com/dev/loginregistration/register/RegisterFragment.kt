/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.dev.loginregistration.R
import com.dev.loginregistration.database.UserDatabase
import com.dev.loginregistration.database.UserRepository
import com.dev.loginregistration.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = UserDatabase.getInstance(application).userDatabaseDao

        val repository = UserRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.viewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                navigateToLogin()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.errortoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields.", Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.hideToast()
            }
        })

        registerViewModel.errortoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError.isNotEmpty()) {
                Toast.makeText(requireContext(), hasError, Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.hideToastUserName()
            }
        })

        registerViewModel.errortoastPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError.isNotEmpty()) {
                Toast.makeText(requireContext(), hasError, Toast.LENGTH_SHORT)
                    .show()
                registerViewModel.hideToastPassword()
            }
        })

        return binding.root
    }

    private fun navigateToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

}