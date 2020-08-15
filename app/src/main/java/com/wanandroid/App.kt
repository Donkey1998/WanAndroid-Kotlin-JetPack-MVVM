package com.wanandroid

import android.app.Application
import android.content.Context
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
    }
}
