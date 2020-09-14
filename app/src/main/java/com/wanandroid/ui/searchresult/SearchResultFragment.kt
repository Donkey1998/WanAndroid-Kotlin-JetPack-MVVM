package com.wanandroid.ui.searchresult

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wanandroid.App
import com.wanandroid.BR
import com.wanandroid.BrowserActivity
import com.wanandroid.R
import com.wanandroid.adapter.BaseBindAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentSerachResultBinding
import com.wanandroid.model.resultbean.Article
import com.wanandroid.ui.first.ArticleViewModel
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.empty.view.*
import kotlinx.android.synthetic.main.fragment_serach_result.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Donkey
 * on 2020/9/12
 */
class SearchResultFragment:BaseVMFragment<FragmentSerachResultBinding>(R.layout.fragment_serach_result) {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val searchResultArticleAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_article_constraint, BR.article) }
    private var searchKey : String = ""
    override fun initView() {
        binding.run {
            viewModel = articleViewModel
            adapter= searchResultArticleAdapter
        }
        searchResultArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,searchResultArticleAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,searchResultArticleAdapter.data[position].title)
                val intent = Intent(activity, BrowserActivity::class.java)
                intent.putExtras(bundle);
                startActivity(intent)
            }
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, searchResultRecycleView) //上拉加载更多
        }
        searchResultRefreshLayout.setOnRefreshListener {
            searchResultArticleAdapter.setEnableLoadMore(false)
            search(searchKey)
        }
    }

    private fun loadMore(){
        articleViewModel.getSearchResultList(false,searchKey)
    }

    fun search(search :String){
        // TODO 当搜索结果为空时  再次搜索会显示空白页 为了优化将空白页的ic和text更换为加载中
        val emptyLayout: View = layoutInflater.inflate(R.layout.empty, null)
        emptyLayout.tv_empty.text = "加载中..."
        emptyLayout.ic_empty.setImageResource(R.drawable.ic_loading)
        emptyLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        searchResultArticleAdapter.emptyView = emptyLayout
        searchKey = search
        articleViewModel.getSearchResultList(true,search)
    }

    override fun initData() {
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                searchResultArticleAdapter.run {
                    setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list.datas)
                        if(list.datas.isEmpty()){
                            // TODO 空白页待优化 可以做成一个错误提示、空白页...共用界面
                            val emptyLayout: View = layoutInflater.inflate(R.layout.empty, null)
                            emptyLayout.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            emptyView = emptyLayout
                        }
                    }else{
                        addData(list.datas)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) searchResultArticleAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}