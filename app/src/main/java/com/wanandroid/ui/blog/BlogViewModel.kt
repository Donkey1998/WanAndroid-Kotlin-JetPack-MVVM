package com.wanandroid.ui.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.BlogRepository
import com.wanandroid.model.resultbean.BlogType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 2020/9/5
 */
class BlogViewModel :BaseViewModel() {
    private val blogRepository = BlogRepository()
    private val _mBlogTypeList: MutableLiveData<List<BlogType>> = MutableLiveData()
    val blogTypeList: LiveData<List<BlogType>>
        get() = _mBlogTypeList


    fun getBlogType() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { blogRepository.getBlogType() }
            if (result is ResponseResult.Success)
            {
                _mBlogTypeList.value = result.data;
            } else if (result is ResponseResult.Error) {
            }
        }
    }
}