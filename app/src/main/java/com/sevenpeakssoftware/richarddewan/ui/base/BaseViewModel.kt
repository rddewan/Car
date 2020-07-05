package com.sevenpeakssoftware.richarddewan.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.net.ssl.HttpsURLConnection

/*
this is a base viewModel class.Any viewModel can extend it prevent boilerplate code
 */
abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper

): ViewModel() {

    val messageString: MutableLiveData<String> = MutableLiveData()
    val messageStringId: MutableLiveData<Int> = MutableLiveData()

    /*
    this abstract function need to be implement in sub view model class
    it will be called on activity or fragment is created
     */
    abstract fun onCreate()

    /*
    check network connection
     */
    protected fun checkNetworkConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) true
        else {
            messageStringId.postValue(R.string.network_connection_error)
            false
        }


    /*
    handle network error
     */
    protected fun handleNetworkError(error: Throwable?) =
        error?.let {
            //log the error with timber / send to firebase crashlytics
            Timber.e(it)

            //helper class to extract error message
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 -> messageStringId.postValue(R.string.network_default_error)
                    0 -> messageStringId.postValue(R.string.empty_result_error)
                    1 -> messageStringId.postValue(R.string.server_connection_error)
                    HttpsURLConnection.HTTP_UNAUTHORIZED ->
                        messageStringId.postValue(R.string.unauthorized_error)
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringId.postValue(R.string.network_internal_error)
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringId.postValue(R.string.network_server_not_available)
                    HttpsURLConnection.HTTP_CONFLICT ->
                        messageStringId.postValue(R.string.duplicate_record)
                    else -> messageString.postValue(message)
                }
            }
        }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}