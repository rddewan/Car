package com.sevenpeakssoftware.richarddewan.data.remote.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ArticlesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("serverTime")
    val serverTime: Int
)