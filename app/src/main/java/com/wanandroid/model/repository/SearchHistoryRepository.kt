package com.wanandroid.model.repository

import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.BlogType
import com.wanandroid.model.resultbean.PopularSearch

/**
 * Created by Donkey
 * on 2020/9/12
 */
class SearchHistoryRepository:BaseRepository() {
    suspend fun getPopularSearch(): ResponseResult<List<PopularSearch>> {
        return safeApiCall(call = { requestPopularSearch() }, errorMessage = "")
    }
    private suspend fun requestPopularSearch():ResponseResult<List<PopularSearch>> {
        val response = WanRetrofitClient.service.getPopularSearchList()
        return executeResponse(response)
    }
}