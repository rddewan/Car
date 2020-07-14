package com.sevenpeakssoftware.richarddewan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import io.reactivex.Single


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMany(articleList: List<ArticleEntity>): List<Long>


    @Query("SELECT * FROM articles")
    fun getArticles(): Single<List<ArticleEntity>>



}