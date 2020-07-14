package com.sevenpeakssoftware.richarddewan.data.repository

import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getApiArticles() = networkService.getArticlesList()
        .map {
            it.content
        }
        .flatMap {
            Observable.fromIterable(it)
                .map {content ->
                    ArticleEntity(
                        articleId = content.id,
                        title = content.title,
                        ingress = content.ingress,
                        image = content.image,
                        created =  Date(content.created * 1000L),
                        changed = Date(content.changed * 1000L)
                    )
                }
                .toList()
        }


    fun insertMany(articleList: List<ArticleEntity>) =
        databaseService.articleDao().insertMany(articleList)

    fun getDbArticles() = databaseService.articleDao().getArticles()
}