package com.wanandroid.model.http

import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.Guide
import com.wanandroid.model.resultbean.Project
import com.wanandroid.model.resultbean.User
import retrofit2.http.*

/**
 * Created by Donkey
 * on 2020/8/4
 */
interface WanService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): WanResponse<User>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/navi/json")
    suspend fun getGuide():WanResponse<List<Guide>>

    @GET("/project/tree/json")
    suspend fun getProject():WanResponse<List<Project>>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): WanResponse<ArticleList>
}
