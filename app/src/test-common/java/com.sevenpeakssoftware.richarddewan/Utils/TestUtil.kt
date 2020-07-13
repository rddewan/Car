package com.sevenpeakssoftware.richarddewan.Utils

import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.response.ArticlesResponse
import com.sevenpeakssoftware.richarddewan.data.remote.response.Content
import java.util.*

object TestUtil {
    /*
    article entity
     */
    val articleEntity = ArticleEntity(
        articleId = 1,
        title = "title",
        ingress = "test",
        image = "image",
        created = Date(1000),
        changed = Date(1000)
    )

    val articleEntity2 = ArticleEntity(
        articleId = 2,
        title = "title2",
        ingress = "test2",
        image = "image2",
        created = Date(1000),
        changed = Date(1000)
    )

    private val article1 = ArticleEntity(
        1, 1001, "title1", "ingress1", "image1",
        Date(1511968425), Date(1511968425)
    )
    private val article2 = ArticleEntity(
        2, 1002, "title2", "ingress2", "image2",
        Date(1511968425), Date(1511968425)
    )
    private val article3 = ArticleEntity(
        3, 1003, "title3", "ingress3", "image3",
        Date(1511968425), Date(1511968425)
    )
    private val article4 = ArticleEntity(
        4, 1004, "title4", "ingress4", "image4",
        Date(1511968425), Date(1511968425)
    )
    val articleList = listOf(article1, article2, article3, article4)


    /*
    response
     */

    val content1 = Content(
        1001, "title1", "ingress1", "image1",
        1511968425, 1511968425
    )
    private val content2 = Content(
        1002, "title2", "ingress2", "image2",
        1511968425, 1511968425
    )
    private val content3 = Content(
        1003, "title3", "ingress3", "image3",
        1511968425, 1511968425
    )
    private val content4 = Content(
        1004, "title4", "ingress4", "image4",
        1511968425, 1511968425
    )
    val response = listOf(content1, content2, content3, content4)

    val articlesResponse =
        ArticlesResponse("status", listOf(content1, content2, content3, content4), 1511968425)
}