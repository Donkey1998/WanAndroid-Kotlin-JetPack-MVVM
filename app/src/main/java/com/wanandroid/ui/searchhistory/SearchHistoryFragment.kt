package com.wanandroid.ui.searchhistory

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.wanandroid.App
import com.wanandroid.BR
import com.wanandroid.R
import com.wanandroid.adapter.BaseBindAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentSearchHistoryBinding
import com.wanandroid.model.resultbean.PopularSearch
import com.wanandroid.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Donkey
 * on 2020/9/12
 */
class SearchHistoryFragment :BaseVMFragment<FragmentSearchHistoryBinding>(R.layout.fragment_search_history){
    private val searchHistoryViewModel by viewModel<SearchHistoryViewModel>()
    private val popularSearchItemAdapter by lazy { BaseBindAdapter<PopularSearch>(R.layout.item_popular_search, BR.popularSearch) }
    override fun initView() {
        binding.run {
            adapter=popularSearchItemAdapter
        }
        popularSearchItemAdapter.run {
            setOnItemClickListener { _, _, position ->
                search(popularSearchItemAdapter.data[position].name)
            }
        }
      //TODO bug注意点
     //当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。（其实可以直接设置为true，当需要改变宽高的时候就用notifyDataSetChanged()去整体刷新一下）
     //rv_hot.setHasFixedSize(true)  当默认键盘弹起时RecyclerView会不刷新经检查是因为setHasFixedSize(true)的原因
        rv_hot.layoutManager = GridLayoutManager(App.getContext(), 3)
    }

    override fun initData() {
        searchHistoryViewModel.getPopularSearch()
    }

    override fun startObserve() {
        searchHistoryViewModel.popularSearchList.observe(viewLifecycleOwner, Observer {
            popularSearchItemAdapter.run{
                addData(it)
            }
        })
    }
    private fun search(key: String) {
        if (activity is SearchActivity) {
            val activity: SearchActivity? = activity as SearchActivity?
            activity?.search(key)
        }
    }
}