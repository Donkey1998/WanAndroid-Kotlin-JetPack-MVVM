package com.wanandroid.model.repository

import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.Guide

/**
 * Created by Donkey
 * on 2020/8/20
 */
class GuideRepository : BaseRepository() {
    suspend fun getGuide(): ResponseResult<List<Guide>> {
        return safeApiCall(call = { requestGuideList() }, errorMessage = "")
    }

    private suspend fun requestGuideList(): ResponseResult<List<Guide>> {
        val response = WanRetrofitClient.service.getGuide()

        return executeResponse(response)

    }
}