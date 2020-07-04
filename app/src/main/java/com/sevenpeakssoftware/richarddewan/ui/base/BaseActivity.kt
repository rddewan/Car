package com.sevenpeakssoftware.richarddewan.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.sevenpeakssoftware.richarddewan.CarApplication
import com.sevenpeakssoftware.richarddewan.di.component.ActivityComponent
import com.sevenpeakssoftware.richarddewan.di.component.DaggerActivityComponent
import com.sevenpeakssoftware.richarddewan.di.module.ActivityModule
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/*
base class for activity. Any activity can extend it prevent boilerplate code
 */
abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        //provide layout through sub class
        setContentView(provideLayout())
        //implement view model oncreate method
        viewModel.onCreate()
        //setup live data observer
        setupObserver()
        //setup view
        setupView(savedInstanceState)
    }

    private fun buildActivityComponent() =
        DaggerActivityComponent.builder()
            .applicationComponent((application as CarApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    /*
    this abstract function need to be implemented at sub class
     */
    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun setupObserver()

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}