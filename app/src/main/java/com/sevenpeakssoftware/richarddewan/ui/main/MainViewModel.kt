package com.sevenpeakssoftware.richarddewan.ui.main

import androidx.lifecycle.MutableLiveData
import com.sevenpeakssoftware.richarddewan.data.remote.response.ArticlesResponse
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.ui.base.BaseViewModel
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val articlesRepository: ArticlesRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val articlesResponse: MutableLiveData<List<ArticlesResponse>> = MutableLiveData()
    private val articleList = ArrayList<ArticlesResponse>()

    override fun onCreate() {
        getArticles()
    }

    fun getArticles(){
        isLoading.value = true
        if (checkNetworkConnectionWithMessage()){
            compositeDisposable.add(
                articlesRepository.getArticles()
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
                            isLoading.postValue(false)

                        }
                    )
            )
        }

    }
}