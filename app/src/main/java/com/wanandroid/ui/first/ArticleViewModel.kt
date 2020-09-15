package com.wanandroid.ui.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.*
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
        object Blog: ArticleType() //公众号
        object Question : ArticleType() //每日一问
        object Search : ArticleType() //搜索
    }

    private val firstRepository = FirstRepository()
    private val lastedProjectRepository = LastedProjectRepository();
    private val squareRepository = SquareRepository();
    private val blogRepository = BlogRepository()
    private val questionRepository =QuestionRepository()
    private val searchResultRepository = SearchResultRepository()
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
    fun getBlogArticleList(isRefresh: Boolean = false, id: Int) {
        if(isRefresh){
            currentPage=1//获取公众号数据页数是从1开始的
        }
        getArticleList(ArticleType.Blog,isRefresh,id)
    }

    fun getQuestionList(isRefresh: Boolean = false) = getArticleList(ArticleType.Question, isRefresh)
    fun getSearchResultList(isRefresh: Boolean = false,searchKey: String) = getArticleList(ArticleType.Search, isRefresh,0,searchKey)

    private fun getArticleList(articleType: ArticleType, isRefresh: Boolean = false,id :Int = 0,searchKey :String = "") {
        emitArticleUiState(showLoading =  if(articleType == ArticleType.Blog) currentPage==1 else currentPage==0) //刷新下拉的loading
        if(isRefresh) { //下拉刷新时将currentPage置0  //获取公众号数据页数是从1开始的
            currentPage = if(articleType == ArticleType.Blog) {1} else 0
        }

        viewModelScope.launch (Dispatchers.Main){
            val result = withContext(Dispatchers.IO){
                when (articleType) {
                    ArticleType.First ->  firstRepository.getArticleList(currentPage)
                    ArticleType.LatestProject ->  lastedProjectRepository.getLastedProject(currentPage)
                    ArticleType.Square-> squareRepository.getSquareArticleList(currentPage)
                    ArticleType.Blog -> blogRepository.getBlogArticleList(currentPage,id)
                    ArticleType.Question -> questionRepository.getQuestionList(currentPage)
                    ArticleType.Search -> searchResultRepository.getSearchResultList(currentPage,searchKey)
                }
                }
            if (result is ResponseResult.Success) {
                currentPage++
                val articleList = result.data

                if (articleList.offset >= articleList.total) { //已经是最后一页了
                    emitArticleUiState(showLoading = false, successData = articleList,showEnd = true,isRefresh=isRefresh)
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

