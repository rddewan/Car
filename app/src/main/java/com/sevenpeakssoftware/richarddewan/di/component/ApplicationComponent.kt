package com.sevenpeakssoftware.richarddewan.di.component

import com.sevenpeakssoftware.richarddewan.CarApplication
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.di.module.ApplicationModule
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: CarApplication)

    fun getCompositeDisposable(): CompositeDisposable

    fun getSchedulerProvider(): SchedulerProvider

    fun getDatabaseService(): DatabaseService

    fun getNetworkService() : NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getArticlesRepository(): ArticlesRepository
}