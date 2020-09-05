package com.wanandroid.model.repository

import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.Guide
import com.wanandroid.model.resultbean.Project

/**
 * Created by Donkey
 * on 2020/9/1
 */
class SquareRepository :BaseRepository() {

    suspend fun getSquareArticleList(page : Int): ResponseResult<ArticleList> {
        return safeApiCall(call = { requestSquareList(page) }, errorMessage = "")
    }

    private suspend fun requestSquareList(page : Int): ResponseResult<ArticleList> {
        val response = WanRetrofitClient.service.getSquareArticleList(page)

        return executeResponse(response)

    }
}