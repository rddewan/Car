package com.sevenpeakssoftware.richarddewan.data.remote.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Content(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("ingress")
    val ingress: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("created")
    val created: Long

)