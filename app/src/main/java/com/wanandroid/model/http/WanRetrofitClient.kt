package com.wanandroid.model.http

import com.wanandroid.App
import com.wanandroid.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.File

/**
 * Created by Donkey
 * on 2020/8/4
 */
object WanRetrofitClient : BaseRetrofitClient() {

    val service by lazy { getService(
        WanService::class.java,
        WanService.BASE_URL
    ) }
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        val httpCacheDirectory = File(App.getContext().cacheDir,"responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        //应用拦截器：设置缓存策略
        builder.cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!NetWorkUtils.isNetworkAvailable(App.getContext())) { //没有网络的时候使用缓存
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }

                val response = chain.proceed(request)
                if (!NetWorkUtils.isNetworkAvailable(App.getContext())) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()

                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response// 疑问：为什么要添加这个调用 还没有理解
            }

    }

}

