package com.wanandroid.ui.first

import android.widget.Toast
import androidx.lifecycle.Observer
import com.wanandroid.App
import com.wanandroid.R
import com.wanandroid.adapter.FirstArticleAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentFirstBinding
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class FirstFragment : BaseVMFragment<FragmentFirstBinding>(R.layout.fragment_first) {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val firstArticleAdapter by lazy { FirstArticleAdapter() }

    override fun initView() {
        binding.run {
         viewModel = articleViewModel
         adapter= firstArticleAdapter
        }

        firstArticleAdapter.run {
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, homeRecycleView) //上拉加载更多
        }

    }
    private fun loadMore() {
        articleViewModel.getFirstArticleList(false)
    }

    private fun refresh() {
        articleViewModel.getFirstArticleList(true)
    }


    override fun initData() {
        refresh()
    }

    override fun startObserve() {

        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                firstArticleAdapter.run {
                    firstArticleAdapter.setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list.datas)
                    }else{
                        addData(list.datas)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) firstArticleAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}