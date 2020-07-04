package com.sevenpeakssoftware.richarddewan.di.component

import com.sevenpeakssoftware.richarddewan.di.module.ActivityModule
import com.sevenpeakssoftware.richarddewan.di.scope.ActivityScope
import com.sevenpeakssoftware.richarddewan.ui.main.MainActivity
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}