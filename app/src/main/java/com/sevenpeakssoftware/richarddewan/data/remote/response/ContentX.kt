package com.sevenpeakssoftware.richarddewan.data.remote.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ContentX(
    @SerializedName("type")
    val type: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("description")
    val description: String
)