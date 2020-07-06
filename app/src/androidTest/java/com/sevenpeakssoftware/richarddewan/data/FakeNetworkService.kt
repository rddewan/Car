package com.sevenpeakssoftware.richarddewan.data

import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import com.sevenpeakssoftware.richarddewan.data.remote.response.ArticlesResponse
import com.sevenpeakssoftware.richarddewan.data.remote.response.Content
import io.reactivex.Single

class FakeNetworkService: NetworkService {

    override fun getArticlesList(): Single<ArticlesResponse> {
        val content1 = Content(1001,"title1","ingress1","image1",1511968425,1511968425)
        val content2 = Content(1001,"title2","ingress2","image2",1511968425,1511968425)
        val content3 = Content(1001,"title3","ingress3","image3",1511968425,1511968425)
        val content4 = Content(1001,"title4","ingress4","image4",1511968425,1511968425)

        val response = ArticlesResponse("status", listOf(content1,content2,content3,content4),1511968425)

        return Single.just(response)
    }
}