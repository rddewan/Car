package com.sevenpeakssoftware.richarddewan.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/*
base class for activity. Any activity can extend it prevent boilerplate code
 */
abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    lateinit var viewModel: VM

    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
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

    /*
    this abstract function need to be implemented at sub class
     */
    @LayoutRes
    protected abstract fun provideLayout(): Int

    protected abstract fun setupObserver()

    protected abstract fun setupView(savedInstanceState: Bundle?)

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}