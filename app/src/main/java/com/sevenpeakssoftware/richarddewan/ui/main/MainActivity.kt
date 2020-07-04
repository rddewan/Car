package com.sevenpeakssoftware.richarddewan.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.di.component.ActivityComponent
import com.sevenpeakssoftware.richarddewan.ui.base.BaseActivity


class MainActivity : BaseActivity<MainViewModel>() {

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun provideLayout(): Int = R.layout.activity_main

    override fun setupObserver() {
        viewModel.isLoading.observe(this, Observer {

        })

        viewModel.articlesResponse.observe(this, Observer {

        })
    }

    override fun setupView(savedInstanceState: Bundle?) {

    }


}