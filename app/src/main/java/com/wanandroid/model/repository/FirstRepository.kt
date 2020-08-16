package com.wanandroid.model.repository

import android.util.Log
import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.User

/**
 * Created by Donkey
 * on 2020/8/13
 */
class FirstRepository: BaseRepository() {

    suspend fun getArticleList(page: Int): ResponseResult<ArticleList> {
        return safeApiCall(call = { requestArticleList(page) }, errorMessage = "")
    }

    private suspend fun requestArticleList(page: Int): ResponseResult<ArticleList> {
        val response = WanRetrofitClient.service.getHomeArticles(page)

        return executeResponse(response)

    }
}