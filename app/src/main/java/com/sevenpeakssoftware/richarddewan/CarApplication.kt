package com.sevenpeakssoftware.richarddewan

import android.app.Application
import com.sevenpeakssoftware.richarddewan.utils.log.DebugTree
import com.sevenpeakssoftware.richarddewan.utils.log.ReleaseTree
import timber.log.Timber

class CarApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        setupLogging()
    }

    private fun setupLogging(){
        if (BuildConfig.DEBUG) Timber.plant(DebugTree()) else  Timber.plant(ReleaseTree())
    }
}