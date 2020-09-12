package com.wanandroid.ui.searchhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.SearchHistoryRepository
import com.wanandroid.model.resultbean.PopularSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 2020/9/12
 */
class SearchHistoryViewModel :BaseViewModel() {
    private val searchHistoryRepository = SearchHistoryRepository()
    private val _mPopularSearchList: MutableLiveData<List<PopularSearch>> = MutableLiveData()
    val popularSearchList: LiveData<List<PopularSearch>>
        get() = _mPopularSearchList

    fun getPopularSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { searchHistoryRepository.getPopularSearch() }
            if (result is ResponseResult.Success)
            {
                _mPopularSearchList.value = result.data;
            } else if (result is ResponseResult.Error) {
            }
        }
    }
}