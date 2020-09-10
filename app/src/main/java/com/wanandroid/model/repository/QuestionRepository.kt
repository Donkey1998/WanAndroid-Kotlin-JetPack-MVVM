package com.wanandroid.model.repository

import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.ArticleList

/**
 * Created by Donkey
 * on 2020/9/8
 */
class QuestionRepository : BaseRepository(){

    suspend fun getQuestionList(page: Int): ResponseResult<ArticleList> {
        return safeApiCall(call = { requestQuestionList(page) }, errorMessage = "")
    }

    private suspend fun requestQuestionList(page: Int): ResponseResult<ArticleList> {
        val response = WanRetrofitClient.service.getQuestions(page)

        return executeResponse(response)

    }
}