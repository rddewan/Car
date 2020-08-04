package com.sevenpeakssoftware.richarddewan.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sevenpeakssoftware.richarddewan.TestUtils.TestUtil
import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService

import io.reactivex.Single
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
    fun getArticlesList(){
        val response = TestUtil.articlesResponse

        doReturn(Single.just(response))
            .`when`(networkService)
            .getArticlesList()

        articlesRepository.getApiArticles()
        //verify networkService.getArticlesList was called
        verify(networkService).getArticlesList()
    }

   /*
   when articlesRepository.getApiArticles() fun is called should return ArticleEntity
    */
    @Test
    fun when_getApiArticles_return_articleEntity(){
        val response  = TestUtil.articlesResponse

        doReturn(Single.just(response))
            .`when`(networkService)
            .getArticlesList()

        val entity = articlesRepository.getApiArticles()
        println(entity.blockingGet())

    }

    /*
   when articlesRepository.insertMany() fun is called
   return list of inserted row
    */
    @Test
    fun when_insertMany_return_list_of_row_number(){
        val articleList = TestUtil.articleList

        doReturn(articleDao)
            .`when`(databaseService).articleDao()

        doReturn(listOf<Long>(1,2,3,4))
            .`when`(articleDao)
            .insertMany(articleList)


        val result = articlesRepository.insertMany(articleList)
        println(result.toString())

    }

    /*
  when articlesRepository.getDbArticles() fun is called
  return Articles list from local db as inserted row id
   */
    @Test
    fun when_getDbArticles_return_articles_list_from_db(){
        val articleList = TestUtil.articleList

        doReturn(articleDao)
            .`when`(databaseService).articleDao()

        doReturn(Single.just(articleList))
            .`when`(articleDao).getArticles()

        val result = articlesRepository.getDbArticles().blockingGet()
        println(result)


    }
}