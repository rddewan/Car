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

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val articlesResponse: MutableLiveData<ArrayList<Content>> = MutableLiveData()
    private val articleList = ArrayList<Content>()

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
                    .map {
                        articleList.clear()
                        it.content
                    }
                    .flattenAsObservable {
                        it
                    }
                    .concatMapSingle {
                        articleList.add(it)
                        articlesRepository.insertOrUpdate(newArticleEntity(it))
                    }
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                           Timber.d(it.toString())
                        },
                        {
                            isLoading.postValue(false)
                            handleNetworkError(it)
                        },
                        {
                            articlesResponse.postValue(articleList)
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
                .flattenAsObservable {
                    articleList.clear()
                    it
                }
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        articleList.add(
                            Content(it.articleId, it.title, it.ingress, it.image, it.created)
                        )

                    },
                    {
                        isLoading.postValue(false)
                        handleNetworkError(it)
                    },
                    {
                        articlesResponse.postValue(articleList)
                        isLoading.postValue(false)

                    }
                )
        )
    }

    /*
    return Article Entity
     */
    private fun newArticleEntity(content: Content): ArticleEntity =
        ArticleEntity(
            articleId = content.id,
            title = content.title,
            ingress = content.ingress,
            image = content.image,
            created = content.created
        )

}