package com.wanandroid.ui.lastedProject

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.wanandroid.App
import com.wanandroid.R
import com.wanandroid.BR
import com.wanandroid.BrowserActivity
import com.wanandroid.adapter.BaseBindAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentLastedprojectBinding
import com.wanandroid.model.resultbean.Article
import com.wanandroid.ui.first.ArticleViewModel
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_lastedproject.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class LastedProjectFragment : BaseVMFragment<FragmentLastedprojectBinding>(R.layout.fragment_lastedproject) {
    private val TAG: String = LastedProjectFragment::class.java.simpleName
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val lastedProjectAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_project, BR.article) }
    override fun initView() {
        binding.run {
            adapter = lastedProjectAdapter
            viewModel = articleViewModel
        }
        lastedProjectAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,lastedProjectAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,lastedProjectAdapter.data[position].title)
                NavHostFragment.findNavController(this@LastedProjectFragment)
                    .navigate(R.id.browserActivity, bundle)
            }
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, lastedProjectRecycleView) //上拉加载更多
        }
    }

    private fun loadMore() {
        articleViewModel.getLastedProjectList(false)
    }

    override fun initData() {
        articleViewModel.getLastedProjectList()
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                lastedProjectAdapter.run {
                    lastedProjectAdapter.setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list.datas)
                    }else{
                        addData(list.datas)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) lastedProjectAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}