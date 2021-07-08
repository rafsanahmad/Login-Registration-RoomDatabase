package com.dev.loginregistration.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.dev.loginregistration.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val args: HomeFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.welcomeMessage.text = "Welcome ${args.username} \n"

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                navigateToLogin()
                viewModel.doneNavigateToLogin()
            }
        })

        return binding.root
    }

    private fun navigateToLogin() {
        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
}