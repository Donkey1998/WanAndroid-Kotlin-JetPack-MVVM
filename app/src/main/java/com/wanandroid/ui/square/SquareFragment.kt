package com.wanandroid.ui.square

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.wanandroid.App
import com.wanandroid.BR
import com.wanandroid.BrowserActivity
import com.wanandroid.R
import com.wanandroid.adapter.BaseBindAdapter
import com.wanandroid.base.BaseFragment
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentSquareBinding
import com.wanandroid.model.resultbean.Article
import com.wanandroid.ui.first.ArticleViewModel
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_lastedproject.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class SquareFragment : BaseVMFragment<FragmentSquareBinding>(R.layout.fragment_square) {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val squareAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_article_constraint, BR.article) }
    override fun initView() {
        binding.run {
            adapter = squareAdapter
            viewModel = articleViewModel
        }
        squareAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,squareAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,squareAdapter.data[position].title)
                NavHostFragment.findNavController(this@SquareFragment)
                    .navigate(R.id.browserActivity, bundle)
            }
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, lastedProjectRecycleView) //上拉加载更多
        }
    }

    private fun loadMore() {
        articleViewModel.getSquareList(false)
    }

    override fun initData() {
        articleViewModel.getSquareList()
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                squareAdapter.run {
                    squareAdapter.setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list.datas)
                    }else{
                        addData(list.datas)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) squareAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}