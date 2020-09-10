package com.wanandroid.ui.question

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
import com.wanandroid.databinding.QuestionProjectBinding
import com.wanandroid.model.resultbean.Article
import com.wanandroid.ui.first.ArticleViewModel
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_blogarticle.*
import kotlinx.android.synthetic.main.question_project.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class QuestionFragment : BaseVMFragment<QuestionProjectBinding>(R.layout.question_project) {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val questionArticleAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_article_constraint, BR.article) }
    override fun initView() {
        binding.run {
            viewModel = articleViewModel
            adapter= questionArticleAdapter
        }
        questionArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,questionArticleAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,questionArticleAdapter.data[position].title)
                NavHostFragment.findNavController(this@QuestionFragment)
                    .navigate(R.id.browserActivity, bundle)
            }
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, questionRecycleView) //上拉加载更多
        }
        questionRefreshLayout.setOnRefreshListener {
            questionArticleAdapter.setEnableLoadMore(false)
            refresh()
        }
    }

    private fun loadMore() {
        articleViewModel.getQuestionList(false)
    }

    private fun refresh() {
        articleViewModel.getQuestionList(true)
    }


    override fun initData() {
        refresh()
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                questionArticleAdapter.run {
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

            if (it.showEnd) questionArticleAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}