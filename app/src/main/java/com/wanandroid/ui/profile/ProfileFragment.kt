package com.wanandroid.ui.profile

import android.util.Log
import com.wanandroid.R
import com.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by Donkey
 * on 3:18 PM
 */
class ProfileFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_profile

    override fun initView() {
        setOnClickListener()
    }

    override fun initData() {

    }

    private fun setOnClickListener(){
        login.setOnClickListener {
            Log.d("ProfileFragment", "login")
        }
        header.setOnClickListener {
            Log.d("ProfileFragment", "header")
        }
        readHistory.setOnClickListener {
            Log.d("ProfileFragment", "readHistory")
        }
        clearCache.setOnClickListener {
            Log.d("ProfileFragment", "clearCache")
        }
        aboutAuthor.setOnClickListener {
            Log.d("ProfileFragment", "aboutAuthor")
        }
        systemSetting.setOnClickListener {
            Log.d("ProfileFragment", "systemSetting")
        }



    }
}