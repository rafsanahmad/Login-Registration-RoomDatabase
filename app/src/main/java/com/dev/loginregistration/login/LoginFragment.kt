package com.dev.loginregistration.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.dev.loginregistration.R
import com.dev.loginregistration.databinding.FragmentLoginBinding
import com.dev.loginregistration.database.UserDatabase
import com.dev.loginregistration.database.UserRepository


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = UserDatabase.getInstance(application).userDatabaseDao

        val repository = UserRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel

        binding.lifecycleOwner = this

        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.showErrorToast()
            }
        })

        loginViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(
                    requireContext(),
                    "User doesn't exist.",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.showErrorToastUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.showErrorToastPassword()
            }
        })

        loginViewModel.navigateToHome.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                navigateHome()
                loginViewModel.doneNavigateToHome()
            }
        })


        return binding.root
    }

    private fun navigateHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToUserDetailsFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}