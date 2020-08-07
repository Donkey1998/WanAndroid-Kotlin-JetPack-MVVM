package com.wanandroid.model.http

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

/**
 * Created by Donkey
 * on 2020/8/4
 */
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> ResponseResult<T>, errorMessage: String): ResponseResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            ResponseResult.Error(errorMessage)
        }
    }

    suspend fun <T : Any> executeResponse(response: WanResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): ResponseResult<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                ResponseResult.Error(response.errorMsg)
            } else {
                successBlock?.let { it() }
                ResponseResult.Success(response.data)
            }
        }
    }
}


