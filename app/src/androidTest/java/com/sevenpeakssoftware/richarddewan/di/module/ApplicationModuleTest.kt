package com.sevenpeakssoftware.richarddewan.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sevenpeakssoftware.richarddewan.BuildConfig
import com.sevenpeakssoftware.richarddewan.CarApplication
import com.sevenpeakssoftware.richarddewan.data.FakeNetworkService
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import com.sevenpeakssoftware.richarddewan.data.remote.Networking
import com.sevenpeakssoftware.richarddewan.di.qualifier.ApplicationContext
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.RxSchedulerProvider
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Module
class ApplicationModuleTest(private val application: CarApplication){
    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        /*Networking.create(
            BuildConfig.BASE_URL,
            application.cacheDir,
            1024 * 1024 * 10 //10MB
        )*/
        FakeNetworkService()

    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(
            application,
            DatabaseService::class.java,
            "cars_db"
        ).build()

}