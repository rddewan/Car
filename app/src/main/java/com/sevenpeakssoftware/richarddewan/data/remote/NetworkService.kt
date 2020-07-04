package com.sevenpeakssoftware.richarddewan.data.remote

import com.sevenpeakssoftware.richarddewan.data.remote.response.ArticlesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @Headers(Networking.HEADER_ACCEPT)
    @GET(Endpoints.GET_ARTICLES_LIST)
    fun getArticlesList(): Single<List<ArticlesResponse>>
}