package com.sevenpeakssoftware.richarddewan.di

import android.content.Context
import com.sevenpeakssoftware.richarddewan.CarApplication
import com.sevenpeakssoftware.richarddewan.di.component.ApplicationComponentTest
import com.sevenpeakssoftware.richarddewan.di.component.DaggerApplicationComponentTest
import com.sevenpeakssoftware.richarddewan.di.module.ApplicationModuleTest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/*
rule needed for setting up dagger for ui testing
 */
class TestComponentRule(private  val context: Context): TestRule {
    var testComponent: ApplicationComponentTest? = null

    fun getContext() = context
    private fun setupDaggerTestComponentInApplication(){
        val application = context.applicationContext as CarApplication
        testComponent = DaggerApplicationComponentTest.builder()
            .applicationModuleTest(ApplicationModuleTest(application))
            .build()

        application.setComponent(testComponent!!)
    }

    override fun apply(base: Statement, description: Description?): Statement {
        return object: Statement(){
            override fun evaluate() {
                try {
                    setupDaggerTestComponentInApplication()
                    base.evaluate()
                }
                finally {
                    testComponent = null
                }
            }

        }
    }

}