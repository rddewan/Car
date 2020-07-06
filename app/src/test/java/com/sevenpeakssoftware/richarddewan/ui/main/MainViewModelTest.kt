package com.sevenpeakssoftware.richarddewan.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sevenpeakssoftware.richarddewan.R
import com.sevenpeakssoftware.richarddewan.data.local.entity.ArticleEntity
import com.sevenpeakssoftware.richarddewan.data.remote.response.ArticlesResponse
import com.sevenpeakssoftware.richarddewan.data.remote.response.Content
import com.sevenpeakssoftware.richarddewan.data.repository.ArticlesRepository
import com.sevenpeakssoftware.richarddewan.utils.getOrAwaitValue
import com.sevenpeakssoftware.richarddewan.utils.network.NetworkHelper
import com.sevenpeakssoftware.richarddewan.utils.rx.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


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

    @Test
    fun givenNoInternet_return_network_error_get_data_from_local_database(){
        val article1 = ArticleEntity(1,1001,"title1","ingress1","image1",
            Date(1511968425),Date(1511968425))
        val article2 = ArticleEntity(2,1002,"title2","ingress2","image2",
            Date(1511968425),Date(1511968425))
        val article3 = ArticleEntity(3,1003,"title3","ingress3","image3",
            Date(1511968425),Date(1511968425))
        val article4 = ArticleEntity(4,1004,"title4","ingress4","image4",
            Date(1511968425),Date(1511968425))

        val content1 = Content(1001,"title1","ingress1","image1",
            1511968425,1511968425)
        val content2 = Content(1002,"title2","ingress2","image2",
            1511968425,1511968425)
        val content3 = Content(1003,"title3","ingress3","image3",
            1511968425,1511968425)
        val content4 = Content(1004,"title4","ingress4","image4",
            1511968425,1511968425)

        doReturn(false)
            .`when`(networkHelper)
            .isNetworkConnected()

        doReturn(Single.just(listOf(article1,article2,article3,article4)))
            .`when`(articlesRepository).getDbArticles()

        mainViewModel.getArticles()
        testScheduler.triggerActions()

        assertThat(mainViewModel.messageStringId.getOrAwaitValue(),
            `is`(R.string.network_connection_error))

        assertThat(mainViewModel.isLoading.getOrAwaitValue(),
        `is`(false))

        assertThat(mainViewModel.articlesResponse.getOrAwaitValue(),
        `is`(arrayListOf(content1,content2,content3,content4)))

    }

    @Test
    fun givenInternet_return_api_data(){
        val article1 = ArticleEntity(1,1001,"title1","ingress1","image1",
            Date(1511968425),Date(1511968425))

        val content1 = Content(1001,"title1","ingress1","image1",
            1511968425,1511968425)
        val content2 = Content(1002,"title2","ingress2","image2",
            1511968425,1511968425)
        val content3 = Content(1003,"title3","ingress3","image3",
            1511968425,1511968425)
        val content4 = Content(1004,"title4","ingress4","image4",
            1511968425,1511968425)
        val response = ArticlesResponse("status", listOf(content1,content2,content3,content4),1511968425)

        doReturn(true)
            .`when`(networkHelper)
            .isNetworkConnected()

        doReturn(Single.just(response))
            .`when`(articlesRepository)
            .getApiArticles()



        mainViewModel.articlesResponse.value = arrayListOf(content1,content2,content3,content4)
        mainViewModel.articleList = arrayListOf(content1,content2,content3,content4)


        mainViewModel.onCreate()
        testScheduler.triggerActions()

        assertThat(mainViewModel.isLoading.getOrAwaitValue(),
            `is`(false))

        assertThat(4,`is`(mainViewModel.articleList.size))

        assertThat(mainViewModel.articlesResponse.getOrAwaitValue().size,
            `is`(4))



    }


}