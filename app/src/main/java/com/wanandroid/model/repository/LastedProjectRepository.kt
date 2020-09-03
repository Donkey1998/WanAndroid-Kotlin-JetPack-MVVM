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
class LastedProjectRepository :BaseRepository() {

    suspend fun getLastedProject(page : Int): ResponseResult<ArticleList> {
        return safeApiCall(call = { requestLastedProjectList(page) }, errorMessage = "")
    }

    private suspend fun requestLastedProjectList(page : Int): ResponseResult<ArticleList> {
        val response = WanRetrofitClient.service.getLastedProject(page)

        return executeResponse(response)

    }
}