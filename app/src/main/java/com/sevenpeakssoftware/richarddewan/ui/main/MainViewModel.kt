package com.sevenpeakssoftware.richarddewan.ui.main

import androidx.lifecycle.MutableLiveData
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.response.Content
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.ui.base.BaseViewModel
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.*


class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val articlesResponse: MutableLiveData<List<ArticleEntity>> = MutableLiveData()

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
                            /*
                            do in background thread as we need to insert record to room db
                             */
                            isLoading.postValue(false)
                            //update live data
                            articlesResponse.postValue(it)
                            //insert to local db
                            articlesRepository.insertMany(it)
                        },
                        {
                            isLoading.postValue(false)
                            handleException(it)
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
                .observeOn(schedulerProvider.main())
                .subscribe(
                    {
                        isLoading.value = false
                        articlesResponse.value = it
                    },
                    {
                        isLoading.value = false

                        handleException(it)
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