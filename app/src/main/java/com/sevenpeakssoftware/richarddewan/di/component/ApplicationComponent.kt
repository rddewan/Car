package com.sevenpeakssoftware.richarddewan.di.component

import com.sevenpeakssoftware.richarddewan.CarApplication
import com.sevenpeakssoftware.richarddewan.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: CarApplication)
}