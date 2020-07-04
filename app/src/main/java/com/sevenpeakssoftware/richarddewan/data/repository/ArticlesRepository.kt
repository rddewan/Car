package com.sevenpeakssoftware.richarddewan.data.repository

import com.sevenpeakssoftware.richarddewan.data.remote.NetworkService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesRepository @Inject constructor (private val networkService: NetworkService){

    fun getArticles() = networkService.getArticlesList()
}