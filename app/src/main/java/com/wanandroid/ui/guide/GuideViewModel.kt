package com.wanandroid.ui.guide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.GuideRepository
import com.wanandroid.model.resultbean.Article
import com.wanandroid.model.resultbean.Guide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 2020/8/20
 */
class GuideViewModel:BaseViewModel() {
    private val guideRepository = GuideRepository()
    private val _uiState = MutableLiveData<List<Guide>>();

    val uiState: LiveData<List<Guide>>
        get() = _uiState

   fun getGuideList(){
        viewModelScope.launch(Dispatchers.Main) {
             val result= withContext(Dispatchers.IO){ guideRepository.getGuide()}
            if (result is ResponseResult.Success) {
                val guideList = result.data
                _uiState.value = guideList

            } else if (result is ResponseResult.Error) {
            }
        }
    }

}
