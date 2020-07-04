package com.sevenpeakssoftware.richarddewan.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenpeakssoftware.richarddewan.di.qualifier.ActivityContext
import com.sevenpeakssoftware.richarddewan.ui.base.BaseActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ActivityModule (private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    @Singleton
    @ActivityContext
    fun provideContext(): Context = activity

}