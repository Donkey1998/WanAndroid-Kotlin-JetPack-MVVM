package com.wanandroid.model.repository

import android.util.Log
import com.wanandroid.model.http.BaseRepository
import com.wanandroid.model.ResultBean.User
import com.wanandroid.model.http.WanRetrofitClient
import com.wanandroid.model.http.ResponseResult

/**
 * Created by Donkey
 * on 2020/8/4
 */

class LoginRepository: BaseRepository() {

private val  TAG : String = LoginRepository::class.java.simpleName


    suspend fun login(userName: String, passWord: String): ResponseResult<User> {
        return safeApiCall(call = { requestLogin(userName, passWord) }, errorMessage = "")
    }

    private suspend fun requestLogin(userName: String, passWord: String):ResponseResult<User> {
        val response = WanRetrofitClient.service.login(userName, passWord)

        return executeResponse(response, {
           Log.d(TAG, "请求成功的回调  $response")
        },{
            Log.d(TAG, "请求失败的回调  $response")
        })

    }


}
