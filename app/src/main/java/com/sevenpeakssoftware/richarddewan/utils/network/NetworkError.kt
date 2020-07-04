package com.sevenpeakssoftware.richarddewan.utils.network

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class NetworkError(
    val status: Int = -1,
    @Expose
    @SerializedName("statusCode")
    val statusCode: String = "-1",
    @Expose
    @SerializedName("message")
    val message: String = "Something went wrong"
)