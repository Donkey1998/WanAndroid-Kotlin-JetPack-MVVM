package com.wanandroid.ui.search

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.wanandroid.App
import com.wanandroid.R
import com.wanandroid.base.BaseActivity
import com.wanandroid.customui.searchtitle.SearchTitleListener
import com.wanandroid.ui.searchhistory.SearchHistoryFragment
import com.wanandroid.ui.searchresult.SearchResultFragment
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by Donkey
 * on 3:18 PM
 */
class SearchActivity : BaseActivity() {
    private var mSearchHistoryFragment: SearchHistoryFragment? = null
    private var mSearchResultFragment: SearchResultFragment? = null
    private var mFragmentManager: FragmentManager? = null
    private var mIsResultPage = false
    override fun getLayoutResId() = R.layout.fragment_search

    override fun initView() {
        initFragment()
        search_tile.setSearchTitleListener(object :
            SearchTitleListener {
            override fun leftButtonOnclick() {
                finish()
            }

            override fun search(searchStr: String?) {
                if (!mIsResultPage) {
                    showResultFragment()
                }
            }

            override fun rightButton(searchStr: String?) {
                if (!mIsResultPage) {
                    showResultFragment()
                }
            }

            override fun clearButton(){
                if (mIsResultPage) {
                    showHistoryFragment()
                }
            }

        })
    }

    private fun initFragment(){
        mFragmentManager=supportFragmentManager
        val transaction =supportFragmentManager.beginTransaction()
        val searchHistoryFragment: Fragment? = mFragmentManager!!.findFragmentByTag(SearchHistoryFragment::class.java.name)
        if (searchHistoryFragment == null) {
            mSearchHistoryFragment = SearchHistoryFragment()
            transaction.add(R.id.fl,mSearchHistoryFragment!!,SearchHistoryFragment::class.java.name)
        } else {
            mSearchHistoryFragment = searchHistoryFragment as SearchHistoryFragment
        }
        val searchResultFragment: Fragment? = mFragmentManager!!.findFragmentByTag(SearchResultFragment::class.java.name)
        if (searchResultFragment == null) {
            mSearchResultFragment = SearchResultFragment()
            transaction.add(R.id.fl, mSearchResultFragment!!,SearchResultFragment::class.java.name )
        } else {
            mSearchResultFragment = searchResultFragment as SearchResultFragment
        }
        transaction.show(mSearchHistoryFragment!!)
        transaction.hide(mSearchResultFragment!!)
        transaction.commit()
    }

    override fun initData() {
    }

    private fun showHistoryFragment() {
        mIsResultPage = false
        val t = mFragmentManager!!.beginTransaction()
        t.hide(mSearchResultFragment!!)
        t.show(mSearchHistoryFragment!!)
        t.commit()
    }

    private fun showResultFragment() {
        mIsResultPage = true
        val t = mFragmentManager!!.beginTransaction()
        t.hide(mSearchHistoryFragment!!)
        t.show(mSearchResultFragment!!)
        t.commit()
    }

    fun search( key:String){
        Log.d("SearchActivity","点击了"+key)
    }
}