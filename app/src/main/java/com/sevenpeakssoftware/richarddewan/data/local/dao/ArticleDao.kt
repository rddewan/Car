package com.sevenpeakssoftware.richarddewan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import io.reactivex.Single


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleEntity: ArticleEntity): Long

    @Query("Select * FROM articles WHERE articleId = :articleId")
    fun getArticles(articleId: Long): List<ArticleEntity>

    @Query("SELECT * FROM articles")
    fun getArticles(): Single<List<ArticleEntity>>

    @Query("UPDATE articles SET title =:title, ingress =:ingress, image =:image WHERE articleId =:articleId")
    fun updateArticle(articleId: Long, title: String, ingress: String, image: String): Int

    /*
    insert or update
     */
    fun insertOrUpdate(articleEntity: ArticleEntity): Single<Long> {
        val row: Long
        val articles = getArticles(articleEntity.articleId)
        row = if (articles.isNullOrEmpty()) {
            insert(articleEntity)
        } else {
            updateArticle(
                articleEntity.articleId,
                articleEntity.title,
                articleEntity.ingress,
                articleEntity.image
            ).toLong()
        }
        return Single.just(row)
    }
}