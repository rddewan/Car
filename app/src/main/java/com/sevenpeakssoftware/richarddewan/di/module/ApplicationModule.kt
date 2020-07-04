package com.sevenpeakssoftware.richarddewan.di.module

import android.app.Application
import android.content.Context
import com.sevenpeakssoftware.richarddewan.CarApplication
import com.sevenpeakssoftware.richarddewan.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: CarApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application


    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context = application
}