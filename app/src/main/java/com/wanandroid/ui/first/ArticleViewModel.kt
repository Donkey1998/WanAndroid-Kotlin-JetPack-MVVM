package com.wanandroid.ui.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.FirstRepository
import com.wanandroid.model.repository.LastedProjectRepository
import com.wanandroid.model.repository.SquareRepository
import com.wanandroid.model.resultbean.Article
import com.wanandroid.model.resultbean.ArticleList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 2020/8/8
 */
class ArticleViewModel: BaseViewModel() {
    sealed class ArticleType {
        object First : ArticleType()                 // 首页
        object LatestProject : ArticleType()        // 最新项目
        object Square: ArticleType() //广场
    }

    private val firstRepository = FirstRepository()
    private val lastedProjectRepository = LastedProjectRepository();
    private val squareRepository = SquareRepository();
    private var _uiState = MutableLiveData<ArticleUiModel>()
    private var currentPage = 0

    val uiState: LiveData<ArticleUiModel>
        get() = _uiState

    val refreshHome: ()-> Unit = {  //下拉刷新
        getFirstArticleList(true)
    }

    val refreshLastedProject: ()-> Unit = {  //下拉刷新
        getLastedProjectList(true)
    }

    val refreshSquareList: ()-> Unit = {  //下拉刷新
        getSquareList(true)
    }

    fun getFirstArticleList(isRefresh: Boolean = false) = getArticleList(ArticleType.First,isRefresh);
    fun getLastedProjectList(isRefresh: Boolean = false) = getArticleList(ArticleType.LatestProject,isRefresh);
    fun getSquareList(isRefresh: Boolean = false) = getArticleList(ArticleType.Square,isRefresh);

    private fun getArticleList(articleType: ArticleType, isRefresh: Boolean = false) {
        emitArticleUiState(currentPage==0)
        if(isRefresh) currentPage = 0 //下拉刷新时将currentPage置0
        viewModelScope.launch (Dispatchers.Main){
            val result = withContext(Dispatchers.IO){
                when (articleType) {
                    ArticleType.First ->  firstRepository.getArticleList(currentPage)
                    ArticleType.LatestProject ->  lastedProjectRepository.getLastedProject(currentPage)
                    ArticleType.Square-> squareRepository.getSquareArticleList(currentPage)
                }
                }
            if (result is ResponseResult.Success) {
                currentPage++
                val articleList = result.data

                if (articleList.offset >= articleList.total) { //已经是最后一页了
                    emitArticleUiState(showLoading = false, showEnd = true)
                    return@launch
                }
                emitArticleUiState(showLoading = false, successData = articleList, isRefresh=isRefresh)

            } else if (result is ResponseResult.Error) {
                emitArticleUiState(showLoading = false, showError = "")
            }
        }
    }


    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        successData: ArticleList? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
        needLogin: Boolean? = null
    ) {
        val uiModel = ArticleUiModel(showLoading, showError, successData, showEnd, isRefresh, needLogin)
        _uiState.value = uiModel
    }




    data class ArticleUiModel(
        val showLoading: Boolean,
        val showError: String?,
        val successData: ArticleList?,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean, // 下拉刷新
        val needLogin: Boolean? = null
    )

}

