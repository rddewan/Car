package com.sevenpeakssoftware.richarddewan.ui.main


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.google.android.material.snackbar.Snackbar
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.di.component.ActivityComponent
import com.sevenpeakssoftware.richarddewan.ui.base.BaseActivity
import com.sevenpeakssoftware.richarddewan.ui.main.adaptor.ArticleAdaptor
import com.sevenpeakssoftware.richarddewan.utils.DateTimeUtil
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var mAdaptor: ArticleAdaptor
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var viewPreloadSizeProvider: ViewPreloadSizeProvider<String>
    private lateinit var rvPreLoader :RecyclerViewPreloader<String>

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun provideLayout(): Int = R.layout.activity_main

    override fun setupObserver() {
        viewModel.isLoading.observe(this, Observer {
            pbCar.visibility = if (it) View.VISIBLE else View.GONE
            refreshLayout.isRefreshing = false
        })

        viewModel.articlesResponse.observe(this, Observer {
            mAdaptor.setArticle(it)
            setupRecyclerView()

        })

        viewModel.messageStringId.observe(this, Observer {
            showSnackBar(mainLayout, it)
        })

        viewModel.messageString.observe(this, Observer {
            showSnackBar(mainLayout, it)
        })
    }


    override fun setupView(savedInstanceState: Bundle?) {
        mRecyclerView = findViewById(R.id.rvCar)
        viewPreloadSizeProvider = ViewPreloadSizeProvider()

        //initialise article adaptor
        mAdaptor = ArticleAdaptor(
            arrayListOf(),
            DateTimeUtil(this),
            viewPreloadSizeProvider)

        refreshLayout.setOnRefreshListener {
            viewModel.getArticles()
        }
    }

    private fun setupRecyclerView() {

        rvPreLoader = RecyclerViewPreloader<String>(
            Glide.with(this),
            mAdaptor,
            viewPreloadSizeProvider,
            15
        )

        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdaptor
            addOnScrollListener(rvPreLoader)
        }
    }


    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.white))
            .setTextColor(ContextCompat.getColor(this, R.color.black))
            .show()
    }

    private fun showSnackBar(view: View, message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.white))
            .setTextColor(ContextCompat.getColor(this, R.color.black))
            .show()
    }


}