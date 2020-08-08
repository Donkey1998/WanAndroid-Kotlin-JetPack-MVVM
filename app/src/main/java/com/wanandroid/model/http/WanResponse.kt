package com.wanandroid.model.http

/**
 * Created by Donkey
 * on 2020/8/4
 */
data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)
