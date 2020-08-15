package com.wanandroid.ui.first

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.resultbean.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 2020/8/8
 */
class ArticleViewModel: BaseViewModel() {
    private var _uiState = MutableLiveData<ArticleUiModel>()
    var articleList: MutableList<Article> = ArrayList()
    private var listItemData : Int = 0

    val uiState: LiveData<ArticleUiModel>
        get() = _uiState

    val refreshHome: ()-> Unit = {
        setArticleList()
    }

    fun setArticleList(isRefresh: Boolean? = false) {
        launchOnUI{
            withContext(Dispatchers.IO){
                Thread.sleep(800) //模拟网络请求
                articleList.clear()
                listItemData += 1
                articleList.add(Article(data=listItemData.toString()))
                listItemData += 1
                articleList.add(Article(data=listItemData.toString()))
                listItemData += 1
                articleList.add(Article(data=listItemData.toString()))
                listItemData += 1
                articleList.add(Article(data=listItemData.toString()))
                listItemData += 1
                articleList.add(Article(data=listItemData.toString()))
            }
            val uiModel = ArticleUiModel(listData =articleList, showEnd = false, isRefresh = false)
            _uiState.value = uiModel
        }
    }


    data class ArticleUiModel(
        val listData: MutableList<Article>,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean // 刷新
    ): BaseObservable()

}

