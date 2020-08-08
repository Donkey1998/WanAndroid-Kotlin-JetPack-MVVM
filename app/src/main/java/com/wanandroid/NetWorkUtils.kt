package com.wanandroid

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Donkey
 * on 2020/8/4
 */
class NetWorkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }
    }

}