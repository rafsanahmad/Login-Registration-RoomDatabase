/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.loginregistration

import android.app.Application
import com.dev.loginregistration.utils.Prefs


val prefs: Prefs by lazy {
    MainApplication.prefs!!
}

class MainApplication : Application() {
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(applicationContext)
    }
}
