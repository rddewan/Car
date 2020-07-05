package com.sevenpeakssoftware.richarddewan.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import javax.inject.Singleton


@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@Singleton
abstract class DatabaseService: RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}