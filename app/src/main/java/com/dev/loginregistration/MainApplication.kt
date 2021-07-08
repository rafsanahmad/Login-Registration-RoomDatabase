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
