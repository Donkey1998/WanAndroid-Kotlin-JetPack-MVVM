package com.wanandroid.ui.readhistory

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.wanandroid.BR
import com.wanandroid.BrowserActivity
import com.wanandroid.R
import com.wanandroid.adapter.BaseBindAdapter
import com.wanandroid.base.BaseVMActivity
import com.wanandroid.databinding.ActivityReadHistoryBinding
import com.wanandroid.model.db.dao.ReadHistoryModel
import com.wanandroid.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.activity_read_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReadHistoryActivity : BaseVMActivity() {

    private val binding  by binding<ActivityReadHistoryBinding>(R.layout.activity_read_history)
    private val readHistoryAdapter by lazy {  BaseBindAdapter<ReadHistoryModel>(R.layout.item_read_history,BR.readHistory) }
    private val readHistoryViewModel by viewModel<ReadHistoryViewModel>()
    override fun initView() {
        binding.run {
            viewModel = readHistoryViewModel
            adapter= readHistoryAdapter
        }
        readHistoryAdapter.run {

            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,readHistoryAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,readHistoryAdapter.data[position].title)
                val intent = Intent(this@ReadHistoryActivity, BrowserActivity::class.java)
                intent.putExtras(bundle);
                startActivity(intent);
            }
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, readHistoryRecycleView) //上拉加载更多
        }
        readHistoryRefreshLayout.setOnRefreshListener {
            readHistoryAdapter.setEnableLoadMore(false)
            refresh()
        }
        back_bt.setOnClickListener {
            finish()
        }
    }
    private fun loadMore() {
        readHistoryViewModel.findAllHistory(false)
    }


    private fun refresh() {
        readHistoryViewModel.findAllHistory(true)
    }


    override fun initData() {
        refresh()
    }

    override fun startObserve() {
        readHistoryViewModel.uiState.observe(this@ReadHistoryActivity, Observer {
            it.successData?.let { list ->
                readHistoryAdapter.run {
                    setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list)
                    }else{
                        addData(list)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
                if (it.showEnd) readHistoryAdapter.loadMoreEnd()
            }
        })
    }

}