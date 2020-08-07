package com.wanandroid.ui.home

import android.util.Log
import android.widget.Toast
import com.wanandroid.App
import com.wanandroid.NetWorkUtils
import com.wanandroid.R
import com.wanandroid.base.BaseFragment
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 3:18 PM
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
class HomeFragment : BaseFragment()   {
    override fun getLayoutResId(): Int = R.layout.fragment_home

    private val  TAG : String = HomeFragment::class.java.simpleName
    override fun initView() {
        Log.d(TAG, "initView")
        val loginRepository =  LoginRepository()

        GlobalScope.launch(Dispatchers.Main) {
            val result =  withContext(Dispatchers.IO){loginRepository.login("1600350642","123456") }
            if (result is ResponseResult.Success) {
                Log.d(TAG, result.data.toString())
                Toast.makeText(App.getContext(),"登录成功",Toast.LENGTH_SHORT).show()
            } else if (result is ResponseResult.Error) {
                Log.d(TAG, result.errorMsg)
                Toast.makeText(App.getContext(),result.errorMsg,Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun initData() {
    }
}