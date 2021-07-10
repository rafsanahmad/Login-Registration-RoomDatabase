/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration.home

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.dev.loginregistration.MainActivity
import com.dev.loginregistration.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), LifecycleObserver {
    private val args: HomeFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var foregroundTimer: CountDownTimer
    lateinit var backgroundTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.welcomeMessage.text = "Welcome ${args.username} \n"

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                navigateToLogin()
                viewModel.doneNavigateToLogin()
            }
        })
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        return binding.root
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Log.d("App", "App in background")
        stopTimer(foreground = true)
        startTimer(foreground = false, milliSecondsLeft = viewModel.backgroundMilliSeconds)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Log.d("App", "App in foreground")
        stopTimer(foreground = false)
        startTimer(foreground = true, milliSecondsLeft = viewModel.foregroundMilliSeconds)
    }

    //Start foreground, background timer
    private fun startTimer(foreground: Boolean, milliSecondsLeft: Long) {
        if (foreground) {
            foregroundTimer = object : CountDownTimer(milliSecondsLeft, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000
                    binding.timerMessage.text = "App in foreground: $seconds Seconds"
                }

                override fun onFinish() {
                    Log.d("App", "Foreground timer end")
                    navigateToLogin()
                }
            }
            foregroundTimer.start()
        } else {
            backgroundTimer = object : CountDownTimer(milliSecondsLeft, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000
                    binding.timerMessage.text = "App in background: $seconds Seconds"
                }

                override fun onFinish() {
                    Log.d("App", "Background timer end")
                    navigateToLogin()
                }
            }
            backgroundTimer.start()
        }
    }

    private fun stopTimer(foreground: Boolean) {
        if (foreground) {
            if (::foregroundTimer.isInitialized) {
                foregroundTimer.cancel()
            }
        } else {
            if (::backgroundTimer.isInitialized) {
                backgroundTimer.cancel()
            }
        }
    }

    private fun navigateToLogin() {
        //Stop both timer
        if (::foregroundTimer.isInitialized) {
            foregroundTimer.cancel()
        }
        if (::backgroundTimer.isInitialized) {
            backgroundTimer.cancel()
        }
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        /*val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)*/
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }
}