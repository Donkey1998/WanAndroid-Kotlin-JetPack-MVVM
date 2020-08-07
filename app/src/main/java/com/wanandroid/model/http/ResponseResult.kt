package com.wanandroid.model.http

/**
 * Created by Donkey
 * on 2020/8/4
 */
sealed class ResponseResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResponseResult<T>()
    data class Error(val errorMsg: String) : ResponseResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorMsg=$errorMsg]"
        }
    }
}
