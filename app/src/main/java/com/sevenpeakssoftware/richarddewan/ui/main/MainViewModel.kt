package com.sevenpeakssoftware.richarddewan.ui.main

import androidx.lifecycle.MutableLiveData
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.response.Content
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.ui.base.BaseViewModel
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val articlesResponse: MutableLiveData<ArrayList<ArticleEntity>> = MutableLiveData()
    var articleList = ArrayList<Content>()

    override fun onCreate() {
        getArticles()
    }

    /*
    get the article from api
     */
    fun getArticles() {
        isLoading.value = true

        if (checkNetworkConnectionWithMessage()) {
            compositeDisposable.add(
                articlesRepository.getApiArticles()
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            isLoading.postValue(false)
                            //update live data
                            articlesResponse.postValue(it as ArrayList<ArticleEntity>?)
                            //insert to local db
                            articlesRepository.insertMany(it)
                        },
                        {
                            isLoading.postValue(false)
                        }
                    )

            )
        } else {
            isLoading.value = false

            getDbArticles()
        }

    }

    /*
    get articles from local db
     */
     private fun getDbArticles() {
        isLoading.value = true

        compositeDisposable.add(
            articlesRepository.getDbArticles()
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        isLoading.postValue(false)

                        articlesResponse.postValue(it as ArrayList<ArticleEntity>?)
                    },
                    {
                        isLoading.postValue(false)

                        handleNetworkError(it)
                    }

                )
        )
    }

    /*
    return Article Entity
     */
    fun newArticleEntity(content: Content): ArticleEntity =
        ArticleEntity(
            articleId = content.id,
            title = content.title,
            ingress = content.ingress,
            image = content.image,
            created = Date(content.created),
            changed = Date(content.changed)
        )

}