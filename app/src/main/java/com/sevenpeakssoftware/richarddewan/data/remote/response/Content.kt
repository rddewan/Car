package com.sevenpeakssoftware.richarddewan.data.remote.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Content(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("dateTime")
    val dateTime: String,
    @SerializedName("tags")
    val tags: List<Any>,
    @SerializedName("content")
    val content: List<ContentX>,
    @SerializedName("ingress")
    val ingress: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("created")
    val created: Int,
    @SerializedName("changed")
    val changed: Int
)