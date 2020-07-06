package com.sevenpeakssoftware.richarddewan.ui.main

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.utils.RecyclerViewMatcher.atPositionOnView

import com.sevenpeakssoftware.richarddewan.di.TestComponentRule
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class MainActivityTest {

    private val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    private val main = IntentsTestRule(MainActivity::class.java,true,false)

    @get:Rule
    val chain = RuleChain.outerRule(component).around(main)

    @Before
    fun setup(){

    }

    @Test
    fun testStartActivity(){
        main.launchActivity(Intent(component.getContext(),MainActivity::class.java))

        onView(withId(R.id.refreshLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvCar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.pbCar))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun testRecyclerView(){
        main.launchActivity(Intent(component.getContext(),MainActivity::class.java))

        onView(withId(R.id.rvCar))
            .check(matches(atPositionOnView(0, withText("title1"),R.id.txtTitle)))

        onView(withId(R.id.rvCar))
            .check(matches(atPositionOnView(0, withText("ingress1"),R.id.txtIngress)))

        onView(withId(R.id.rvCar))
            .check(matches(atPositionOnView(0, withText("29 November 2017,10:13 PM"),R.id.txtDate)))

        onView(withId(R.id.rvCar))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
            .check(matches(atPositionOnView(3, withText("title4"),R.id.txtTitle)))


    }

}