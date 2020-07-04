package com.sevenpeakssoftware.richarddewan.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.room.EmptyResultSetException
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.io.IOException
import java.lang.NullPointerException
import java.net.ConnectException
import javax.inject.Singleton

@Singleton
class NetworkHelper constructor(private val context: Context) {

    companion object {
        const val TAG = "NetworkHelper"
    }

    fun isNetworkConnected(): Boolean{
        var result = false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = isCapableNetwork(this,this.activeNetwork)
            } else {
                val networkInfo = this.allNetworks
                for (tempNetworkInfo in networkInfo) {
                    if(isCapableNetwork(this,tempNetworkInfo))
                        result =  true
                }
            }
        }

        return result
    }

    private fun isCapableNetwork(cm: ConnectivityManager, network: Network?): Boolean{
        cm.getNetworkCapabilities(network)?.also {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            }
        }
        return false
    }

    fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {
            if (throwable is EmptyResultSetException)
                return NetworkError(0, "0", throwable.message.toString())

            if (throwable is ConnectException)
                return NetworkError(1, "1")

            if (throwable !is HttpException)
                return defaultNetworkError

            val error = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(throwable.response().errorBody()?.string(), NetworkError::class.java)

            return NetworkError(throwable.code(), error.statusCode, error.message)

        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Log.e(TAG, e.toString())
        }
        return defaultNetworkError
    }
}