package com.wanandroid.model.repository

import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.resultbean.ArticleList

/**
 * Created by Donkey
 * on 2020/9/12
 */
class SearchResultRepository :BaseRepository() {
    suspend fun getSearchResultList(page :Int,key: String): ResponseResult<ArticleList> {
        return  safeApiCall( call = {requestSearchResultList(page,key)},errorMessage = "")
    }

    private suspend fun requestSearchResultList(page :Int,key: String): ResponseResult<ArticleList> {
        val response = WanRetrofitClient.service.getSearchResultList(page,key)

        return executeResponse(response)
    }
}