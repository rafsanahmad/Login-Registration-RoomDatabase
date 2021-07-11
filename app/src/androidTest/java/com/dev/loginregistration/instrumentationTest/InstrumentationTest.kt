/*
 * *
 *  * Created by rafsanahmad on 7/11/21, 9:12 AM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/11/21, 9:12 AM
 *
 */

package com.dev.loginregistration.instrumentationTest

import android.view.View
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dev.loginregistration.MainActivity
import com.dev.loginregistration.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentationTest {

    private val fullName = "Rafsan Ahmad"
    private val username = "rafsan"
    private val password = "Aaaaa1"

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

    @Test
    fun clickRegisterButton_opensLoginUi() {
        //Register to login
        onView(withId(R.id.full_name_text_field)).perform(ViewActions.typeText(fullName))
        onView(withId(R.id.userNameTextField)).perform(ViewActions.typeText(username))
        onView(withId(R.id.passwordTextField)).perform(ViewActions.typeText(password))
        onView(withId(R.id.submitButton)).perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(2000))

        //val scenario = launchFragmentInContainer<LoginFragment>()
        onView(withId(R.id.loginBtn)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        //Login to Home
        onView(withId(R.id.userNameTextField)).perform(ViewActions.typeText(username))
        onView(withId(R.id.passwordTextField)).perform(ViewActions.typeText(password))
        onView(withId(R.id.loginBtn)).perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(2000))

        val fragmentArgs = bundleOf("username" to "rafsan")
        //val scenario2 = launchFragmentInContainer<HomeFragment>(fragmentArgs)
        Espresso.onView(withId(R.id.welcome_message))
            .check(matches(withText("Welcome rafsan \n")))

        //Home to Login
        onView(withId(R.id.logoutBtn)).perform(ViewActions.click())
        //val scenario3 = launchFragmentInContainer<LoginFragment>()
        onView(withId(R.id.loginBtn)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}