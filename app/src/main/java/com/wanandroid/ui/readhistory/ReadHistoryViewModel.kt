package com.wanandroid.ui.readhistory

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.App
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.db.WanDatabase
import com.wanandroid.model.db.dao.ReadHistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Donkey
 * on 2020/9/24
 */
class ReadHistoryViewModel:BaseViewModel() {
    private var _uiState = MutableLiveData<ReadHistoryUiModel>()
    private var currentPage = 0
    private var currentSize = 10;
    val uiState: LiveData<ReadHistoryUiModel>
        get() = _uiState

    @SuppressLint("SimpleDateFormat")
    fun addHistory(link: String, title: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val df =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val readHistoryModel = ReadHistoryModel(link, title, df.format(Date()))
                WanDatabase.getInstance(App.getContext()).readHistoryDao().insert(readHistoryModel)
            }
        }
    }

    fun findAllHistory(isRefresh: Boolean){
        emitArticleUiState(showLoading =isRefresh)
        if(isRefresh){
            currentPage = 0
        }
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO){
                WanDatabase.getInstance(App.getContext()).readHistoryDao().findAll(currentPage,currentSize)
            }
            currentPage++
            emitArticleUiState(showLoading = false,successData=result, showEnd = (result.size<currentSize),isRefresh=isRefresh)
            Log.d("findAllHistory",result.size.toString())
        }
    }

    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        successData:  List<ReadHistoryModel>? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false
    ) {
        val uiModel = ReadHistoryUiModel(
            showLoading,
            showError,
            successData,
            showEnd,
            isRefresh
        )
        _uiState.value = uiModel
    }

    data class ReadHistoryUiModel(
        val showLoading: Boolean,
        val showError: String?,
        val successData:  List<ReadHistoryModel>?,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean // 下拉刷新
    )
}