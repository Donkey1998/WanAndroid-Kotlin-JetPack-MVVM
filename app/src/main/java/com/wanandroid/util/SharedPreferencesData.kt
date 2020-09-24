package com.wanandroid.util

/**
 * Created by Donkey
 * on 2020/9/23
 */
object SharedPreferencesData {
    var name by SharedPreference( "name", "")
    var password by SharedPreference( "password", "")
    var isLogin by SharedPreference( "isLogin", false)
}