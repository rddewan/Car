package com.sevenpeakssoftware.richarddewan.data.repository

import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getApiArticles() = networkService.getArticlesList()

    fun insertOrUpdate(articleEntity: ArticleEntity) = databaseService.articleDao().insertOrUpdate(articleEntity)

    fun getDbArticles() = databaseService.articleDao().getArticles()
}