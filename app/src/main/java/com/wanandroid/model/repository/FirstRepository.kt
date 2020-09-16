package com.wanandroid.model.repository

import android.util.Log
import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.Article
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.Banner
import com.wanandroid.model.resultbean.User

/**
 * Created by Donkey
 * on 2020/8/13
 */
class FirstRepository: BaseRepository() {

    suspend fun getBanners(): ResponseResult<List<Banner>> {
        return safeApiCall(call = {requestBanners()},errorMessage = "")
    }

    private suspend fun requestBanners(): ResponseResult<List<Banner>> =
        executeResponse(WanRetrofitClient.service.getBanner())

    suspend fun getTopArticleList(): ResponseResult<List<Article>> {
        return safeApiCall(call = { requestTopArticleList() }, errorMessage = "")
    }

    private suspend fun requestTopArticleList(): ResponseResult<List<Article>> {
        val response = WanRetrofitClient.service.getTopArticleList()

        return executeResponse(response)

    }

    suspend fun getArticleList(page: Int): ResponseResult<ArticleList> {
        return safeApiCall(call = { requestArticleList(page) }, errorMessage = "")
    }

    private suspend fun requestArticleList(page: Int): ResponseResult<ArticleList> {
        val response = WanRetrofitClient.service.getHomeArticles(page)

        return executeResponse(response)

    }
}