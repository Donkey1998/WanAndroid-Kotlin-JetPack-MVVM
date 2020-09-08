package com.wanandroid.ui.blog

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.wanandroid.App
import com.wanandroid.BR
import com.wanandroid.BrowserActivity
import com.wanandroid.R
import com.wanandroid.adapter.BaseBindAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentBlogarticleBinding
import com.wanandroid.model.resultbean.Article
import com.wanandroid.ui.first.ArticleViewModel
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_blogarticle.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 2020/9/6
 */

class BlogArticleFragment : BaseVMFragment<FragmentBlogarticleBinding>(R.layout.fragment_blogarticle) {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val blogArticleAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_article_constraint, BR.article) }
    private val id by lazy { arguments?.getInt(BLOG_ID) }
    companion object {
        private const val BLOG_ID = "id"
        fun newInstance(id: Int): BlogArticleFragment {
            val fragment = BlogArticleFragment()
            val bundle = Bundle()
            bundle.putInt(BLOG_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun initView() {

        binding.run {
            viewModel = articleViewModel
            adapter= blogArticleAdapter
        }

        blogArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,blogArticleAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,blogArticleAdapter.data[position].title)
                NavHostFragment.findNavController(this@BlogArticleFragment)
                    .navigate(R.id.browserActivity, bundle)
            }
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, blogRecycleView) //上拉加载更多
        }
        blogRefreshLayout.setOnRefreshListener {
            blogArticleAdapter.setEnableLoadMore(false)
            refresh()
        }

    }
    private fun loadMore() {
        id?.let {
            articleViewModel.getBlogArticleList(false,it)
        }
    }

    private fun refresh() {
        id?.let {
            articleViewModel.getBlogArticleList(true,it)
        }
    }


    override fun initData() {
        refresh()
    }

    override fun startObserve() {

        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                blogArticleAdapter.run {
                    setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list.datas)
                    }else{
                        addData(list.datas)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) blogArticleAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}