package com.wanandroid

import android.app.Application
import android.content.Context
import android.util.Log
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import org.koin.core.context.startKoin

/**
 * Created by Donkey
 * on 2020/8/4
 */
class App: Application() {
    companion object {
        var _context: Application? = null
        fun getContext(): Context {
            return _context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        _context = this
        startKoin {
            modules(appModule)
        }
        initSDK()
    }
    private fun initSDK() {

        // Crash 捕捉界面   参考链接 https://blog.csdn.net/huangxiaoguo1/article/details/79053197
        CaocConfig.Builder.create() //程序在后台时，发生崩溃的三种处理方式
            //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
            //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
            //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
            .enabled(true)//false表示对崩溃的拦截阻止。用它来禁用customactivityoncrash框架
            .trackActivities(true)
            .minTimeBetweenCrashesMs(2000) //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理！
            .restartActivity(SplashActivity::class.java)// 重启的 Activity
            .errorActivity(CustomCrashActivity::class.java)// 错误的 Activity
            .eventListener(CustomEventListener()) // 设置监听器
            .apply()
    }

    /**
     * 监听程序崩溃/重启
     */
    private class CustomEventListener : CustomActivityOnCrash.EventListener {
        //程序崩溃回调
        override fun onLaunchErrorActivity() {
            Log.d("CustomEventListener", "程序崩溃回调")
        }

        //重启程序时回调
        override fun onRestartAppFromErrorActivity() {
            Log.d("CustomEventListener", "重启程序时回调")
        }

        //在崩溃提示页面关闭程序时回调
        override fun onCloseAppFromErrorActivity() {
            Log.d("CustomEventListener", "在崩溃提示页面关闭程序时回调")
        }
    }

}
