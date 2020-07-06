package com.sevenpeakssoftware.richarddewan.data.db

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    companion object {
        const val TAG = "DatabaseTest"
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var databaseService: DatabaseService

    private lateinit var articleDao: ArticleDao

    @Before
    fun setup() {
        databaseService = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            DatabaseService::class.java
        ).allowMainThreadQueries()
            .build()

        articleDao = databaseService.articleDao()

        Log.d(TAG, "Database created")
    }

    @After
    fun closeDb() {
        databaseService.close()
        Log.d(TAG, "Database closed")
    }

    /*
    when articleDao.getArticles is called it should return list of articles
     */
    @Test
    fun articleDao_getArticles_return_list_of_articles() {

        articleDao.getArticles().test().assertValue(listOf())
    }

    /*
    when articleDao.insert is called it should return row id of 1
     */
    @Test
    fun articleDao_insert_return_row_one() {

        val articleEntity = ArticleEntity(
            articleId = 1,
            title = "title",
            ingress = "test",
            image = "image",
            created = Date(1000),
            changed = Date(1000)
        )

        val articles = articleDao.insert(articleEntity)
        assertEquals(1, articles)

        articleDao.getArticles()
            .test()
            .assertValue {
            val data = it[0]
            articleEntity.title == data.title
        }

    }

    /*
    when articleDao.insert is called return row id 1 and get the article by row id 1
    should return 1 article
     */
    @Test
    fun articleDao_insert_return_rowId_getArticles_by_id_return_one_article() {
        val articleEntity = ArticleEntity(
            articleId = 1,
            title = "title",
            ingress = "test",
            image = "image",
            created = Date(1000),
            changed = Date(1000)
        )

        val rowId = articleDao.insert(articleEntity)
        val article = articleDao.getArticles(rowId)
        assertEquals(1, article.size)
    }

    /*
    when articleDao.insertOrUpdate() is called it should insert or update and return 1
     */
    @Test
    fun insert_update() {
        val articleEntity = ArticleEntity(
            articleId = 1,
            title = "title",
            ingress = "test",
            image = "image",
            created = Date(1000),
            changed = Date(1000)
        )
        articleDao.insertOrUpdate(articleEntity)
            .test()
            .assertValue {
                it == 1L
            }

    }


}