package com.wanandroid.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.wanandroid.App
import kotlin.reflect.KProperty

/**
 * Created by Donkey
 * on 2020/9/23
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
class SharedPreference<T>(
    private var keyName: String? = null,
    private var defaultValue: T? = null,
    private val pref: String = "default"
) {

    private val prefs: SharedPreferences by lazy {
        App.getContext().applicationContext.getSharedPreferences(pref, Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T  {
        Log.d("SharedPreference", "调用$this 的getValue()")
        return findSharedPreference(keyName!!, defaultValue!!)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        Log.d("SharedPreference", "调用$this 的setValue() value参数值为：$value")
        putSharedPreferences(keyName!!, value)
    }


    /**
     * 查找数据 返回一个具体的对象
     * 没有查找到value 就返回默认的序列化对象，然后经过反序列化返回
     */
    @Suppress("UNCHECKED_CAST")
    private fun  findSharedPreference(key: String, default: T): T =  when (default) {
            is Long -> prefs.getLong(key, default)
            is String ->prefs.getString(key, default)
            is Int -> prefs.getInt(key, default)
            is Boolean ->prefs.getBoolean(key, default)
            is Float -> prefs.getFloat(key, default)
            else -> throw IllegalArgumentException("Unsupported type.")
        } as T



    @SuppressLint("CommitPrefEdits")
    private fun  putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("Unsupported type.")
        }.apply()
    }


    /**
     * 删除全部数据
     */
    fun clear() {
        Log.d("SharedPreference", "调用$this clear()")
        prefs.edit().clear().apply()
    }

    /**
     * 根据key删除存储数据
     */
    fun remove(key: String) {
        Log.d("SharedPreference", "调用$this remove()参数值为：$key")
        prefs.edit().remove(key).apply()
    }

}

