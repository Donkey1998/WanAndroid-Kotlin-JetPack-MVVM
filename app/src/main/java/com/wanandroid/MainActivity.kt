package com.wanandroid

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wanandroid.base.BaseActivity

class MainActivity : BaseActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
    override fun initView() {
        bottomNavigationView = findViewById(R.id.bnv_view)
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host) as NavHostFragment
        val navController = host.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun initData() {
    }
}