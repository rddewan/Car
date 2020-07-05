package com.sevenpeakssoftware.richarddewan.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.di.qualifier.ActivityContext
import com.sevenpeakssoftware.richarddewan.di.scope.ActivityScope
import com.sevenpeakssoftware.richarddewan.ui.base.BaseActivity
import com.sevenpeakssoftware.richarddewan.ui.main.MainViewModel
import com.sevenpeakssoftware.richarddewan.ui.main.adaptor.ArticleAdaptor
import com.sevenpeakssoftware.richarddewan.utils.DateTimeUtil
import com.sevenpeakssoftware.richarddewan.utils.ViewModelProviderFactory
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    @Singleton
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    @ActivityScope
    fun provideArticleAdaptor(): ArticleAdaptor = ArticleAdaptor(ArrayList(),DateTimeUtil(activity))

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        articlesRepository: ArticlesRepository
    ): MainViewModel =
        ViewModelProvider(activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                articlesRepository
            )
        }).get(MainViewModel::class.java)

}