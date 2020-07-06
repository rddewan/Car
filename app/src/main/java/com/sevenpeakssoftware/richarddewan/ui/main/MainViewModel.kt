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
    val articlesResponse: MutableLiveData<ArrayList<Content>> = MutableLiveData()
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
                    .map {
                        articleList.clear()
                        it.content
                    }
                    .flattenAsObservable {
                        it
                    }
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            articleList.add(it)
                        },
                        {
                            isLoading.postValue(false)
                            handleNetworkError(it)
                        },
                        {
                            articlesResponse.postValue(articleList)
                            //loop through array list and insert to local db
                            articleList.forEach{
                                articlesRepository.insertOrUpdate(newArticleEntity(it))
                            }

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
                            Content(it.articleId, it.title, it.ingress, it.image, it.created.time,it.changed.time)
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
            created = Date(content.created),
            changed = Date(content.changed)
        )

}