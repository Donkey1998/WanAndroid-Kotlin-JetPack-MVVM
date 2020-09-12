package com.wanandroid.ui.search

import android.util.Log
import com.wanandroid.R
import com.wanandroid.base.BaseActivity
import com.wanandroid.customui.searchtitle.SearchTitleListener
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by Donkey
 * on 3:18 PM
 */
class SearchActivity : BaseActivity() {
    override fun getLayoutResId() = R.layout.fragment_search

    override fun initView() {
        search_tile.setSearchTitleListener(object :
            SearchTitleListener {
            override fun leftButtonOnclick() {
                finish()
            }

            override fun search(searchStr: String?) {
                Log.d("搜索", searchStr)
            }

            override fun rightButton(searchStr: String?) {
                Log.d("搜索", "点击了右边的button$searchStr");
            }

            override fun clearButton(){
                Log.d("搜索", "清除")
            }


        })
    }

    override fun initData() {
    }
}