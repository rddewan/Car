package com.sevenpeakssoftware.richarddewan.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
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

        verify(networkService).getArticlesList()
    }


}