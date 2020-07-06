package com.sevenpeakssoftware.richarddewan.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "articleId")
    val articleId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "ingress")
    val ingress: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "created")
    val created: Date,
    @ColumnInfo(name = "changed")
    val changed: Date
)