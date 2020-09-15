package com.wanandroid

import android.content.Intent
import com.wanandroid.base.BaseActivity


/**
 * Created by Donkey
 * on 2020/9/15
 */
class SplashActivity : BaseActivity() {
    override fun getLayoutResId() = R.layout.activity_splash


    override fun initView() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun initData() {
    }
}