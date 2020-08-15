package com.wanandroid.ui.first

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wanandroid.base.BaseViewModel

/**
 * Created by Donkey
 * on 2020/8/8
 */
class ArticleViewModel: BaseViewModel() {
    private var _uiState = MutableLiveData<Int>()
   init {
       _uiState.value = 10
   }

    val uiState: LiveData<Int>
        get() = _uiState


    fun plusOne() {
        val count = _uiState.value?:0
        _uiState.value = count + 1
    }

}

