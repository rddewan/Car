package com.sevenpeakssoftware.richarddewan.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sevenpeakssoftware.richarddewan.Utils.TestUtil
import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import com.sevenpeakssoftware.richarddewan.data.remote.response.ArticlesResponse
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner



@RunWith(MockitoJUnitRunner::class)
class ArticlesRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkService: NetworkService
    @Mock
    lateinit var databaseService: DatabaseService
    @Mock
    private lateinit var articleDao: ArticleDao
    //test subject
    private lateinit var articlesRepository: ArticlesRepository

    @Before
    fun setup(){
        articlesRepository = ArticlesRepository(networkService,databaseService)
    }

    /*
    when getArticlesList() fun is called it should call getArticlesList() from networkService
     */
    @Test
    fun getApiArticles_request_getArticlesList(){

        doReturn(Single.just(ArticlesResponse("status", listOf(),12345678)))
            .`when`(networkService)
            .getArticlesList()

        articlesRepository.getApiArticles()
        //verify networkService.getArticlesList was called
        verify(networkService).getArticlesList()
    }

   /*
   when articlesRepository.insertOrUpdate() fun is called
   verify that articleDao.insertOrUpdate() is called
    */

    @Test
    fun when_articlesRepository_insertOrUpdate_called_verify_articleDao_insertOrUpdate_is_called(){
        val articleEntity = TestUtil.articleEntity

        doReturn(articleDao)
            .`when`(databaseService)
            .articleDao()

        doReturn(Single.just(1))
            .`when`(articleDao)
            .insertOrUpdate(articleEntity)


        articlesRepository.insertOrUpdate(articleEntity)
        //verify articleDao.insertOrUpdate was called
        verify(articleDao).insertOrUpdate(articleEntity)
        println("Repository insertOrUpdate called success")


    }

    /*
   when articlesRepository.insertOrUpdate() fun is called
   return 1L as inserted row id
    */
    @Test
    fun when_articlesRepository_insertOrUpdate_return_long_one(){
        val articleEntity = TestUtil.articleEntity

        doReturn(articleDao)
            .`when`(databaseService).articleDao()

        doReturn(Single.just(1L))
            .`when`(articleDao).insertOrUpdate(articleEntity)

        val result = articlesRepository.insertOrUpdate(articleEntity).blockingGet()
        assertEquals(1L,result)
        println("Repository insertOrUpdate insert success $result")

    }

    /*
  when articlesRepository.getDbArticles() fun is called
  return Articles list from local db as inserted row id
   */
    @Test
    fun when_articlesRepository_getDbArticles_return_articles_list_from_db(){

        val articleList = TestUtil.articleList

        doReturn(articleDao)
            .`when`(databaseService).articleDao()

        doReturn(Single.just(articleList))
            .`when`(articleDao).getArticles()

        val result = articlesRepository.getDbArticles().blockingGet()
        assertEquals(4,result.size)
        println("Repository getDbArticles  $result")

    }
}