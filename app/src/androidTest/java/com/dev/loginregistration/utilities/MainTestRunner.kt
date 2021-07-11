/*
 * *
 *  * Created by rafsanahmad on 7/11/21, 6:02 AM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/11/21, 6:02 AM
 *
 */

package com.dev.loginregistration.utilities

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.dev.loginregistration.MainApplication

// A custom runner to set up the instrumented application class for tests.
class MainTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, MainApplication::class.java.name, context)
    }
}
