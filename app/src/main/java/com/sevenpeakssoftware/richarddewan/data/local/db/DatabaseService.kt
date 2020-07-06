package com.sevenpeakssoftware.richarddewan.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.utils.converter.DateConverter
import javax.inject.Singleton


@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
@Singleton
abstract class DatabaseService: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}