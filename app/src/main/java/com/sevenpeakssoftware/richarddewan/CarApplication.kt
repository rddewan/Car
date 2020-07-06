package com.sevenpeakssoftware.richarddewan

import android.app.Application
import com.sevenpeakssoftware.richarddewan.di.component.ApplicationComponent
import com.sevenpeakssoftware.richarddewan.di.component.DaggerApplicationComponent
import com.sevenpeakssoftware.richarddewan.di.module.ApplicationModule
import com.sevenpeakssoftware.richarddewan.utils.log.DebugTree
import com.sevenpeakssoftware.richarddewan.utils.log.ReleaseTree
import timber.log.Timber

class CarApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        //inject dependencies
        injectDependencies()
        //timber logging
        setupLogging()
    }

    private fun injectDependencies(){
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        applicationComponent.inject(this)

    }

    private fun setupLogging(){
        if (BuildConfig.DEBUG) Timber.plant(DebugTree()) else  Timber.plant(ReleaseTree())
    }

    /*
    replace the component with test specific component
     */
    fun setComponent(applicationComponent: ApplicationComponent){
        this.applicationComponent = applicationComponent
    }
}