package com.sevenpeakssoftware.richarddewan.di.component


import com.sevenpeakssoftware.richarddewan.di.module.ApplicationModuleTest
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModuleTest::class])
interface ApplicationComponentTest: ApplicationComponent {
}