package com.sevenpeakssoftware.richarddewan.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.TestUtils.TestUtil
import com.sevenpeakssoftware.richarddewan.TestUtils.TestUtil.content1
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.utils.getOrAwaitValue
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    /*
    run architecture component related background jobs in same thread for testing
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper
    @Mock
    private lateinit var articlesRepository: ArticlesRepository

    private lateinit var testScheduler: TestScheduler

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup(){
        val compositeDisposable = CompositeDisposable()
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        mainViewModel = MainViewModel(
            testSchedulerProvider,
            compositeDisposable,
            networkHelper,
            articlesRepository
        )
    }

    /*
    when no internet connection get articles from local db
     */
    @Test
    fun givenNoInternet_return_network_error_return_article_from_local_database(){

        val articles = TestUtil.articleList
        val response = TestUtil.response

        doReturn(false)
            .`when`(networkHelper)
            .isNetworkConnected()

        doReturn(Single.just(articles))
            .`when`(articlesRepository)
            .getDbArticles()

        mainViewModel.getArticles()
        testScheduler.triggerActions()

        assertThat(mainViewModel.messageStringId.getOrAwaitValue(),
            `is`(R.string.network_connection_error))

        assertThat(mainViewModel.isLoading.getOrAwaitValue(),
        `is`(false))

        assertEquals(mainViewModel.articlesResponse.getOrAwaitValue()[0].title, response[0].title)

        assertEquals(response.size, mainViewModel.articlesResponse.getOrAwaitValue().size)
        println("articlesResponse size : ${mainViewModel.articlesResponse.getOrAwaitValue().size}")


    }

    /*
    given internet connection return content from api
    and verify articlesRepository.insertOrUpdate(article) is called
     */
    @Test
    fun givenInternet_return_api_response(){


        val response = TestUtil.articlesResponse

        doReturn(true)
            .`when`(networkHelper)
            .isNetworkConnected()

        doReturn(Single.just(response))
            .`when`(articlesRepository)
            .getApiArticles()


        mainViewModel.onCreate()
        testScheduler.triggerActions()

        assertThat(mainViewModel.isLoading.getOrAwaitValue(),
            `is`(false))

        val article = mainViewModel.newArticleEntity(content1)



    }


    /*
    given content should return article
     */
    @Test
    fun give_content_return_article(){

        val article = mainViewModel.newArticleEntity(content1)
        assertEquals(content1.id, article.articleId)
        assertEquals(content1.title,article.title)
        assertEquals(content1.ingress,article.ingress)
        assertEquals(content1.image, article.image)

    }


}