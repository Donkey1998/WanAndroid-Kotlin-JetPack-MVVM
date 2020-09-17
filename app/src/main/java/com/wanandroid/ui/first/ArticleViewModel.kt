package com.wanandroid.ui.first

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.*
import com.wanandroid.model.resultbean.Article
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.Banner
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

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
    private var _mBanners = MutableLiveData<List<Banner>>()
    private var currentPage = 0

    val uiState: LiveData<ArticleUiModel>
        get() = _uiState
    val mBanners: LiveData<List<Banner>>
        get() = _mBanners

    @ExperimentalCoroutinesApi
    val refreshHome: ()-> Unit = {  //下拉刷新
        getBannerList()
        getFirstArticleList()
    }

    val refreshLastedProject: ()-> Unit = {  //下拉刷新
        getLastedProjectList(true)
    }

    val refreshSquareList: ()-> Unit = {  //下拉刷新
        getSquareList(true)
    }

    fun getBannerList(){
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO){
                firstRepository.getBanners()
            }
            if(result is ResponseResult.Success) {
                _mBanners.value = result.data
            }

        }
    }

    @ExperimentalCoroutinesApi
    /*
      使用flow将置顶的文章和首页第0页的文章拼接在一起
      flow的使用参考文档 https://www.jianshu.com/p/fe1293e8f15c
     */
    fun getFirstArticleList() {
        currentPage=0;
        emitArticleUiState(showLoading = true)
        viewModelScope.launch(Dispatchers.Main){
            flow {
               val topArticleResult= firstRepository.getTopArticleList()
                val result = firstRepository.getArticleList(0)
                if(result is ResponseResult.Success && topArticleResult is ResponseResult.Success){
                    val articleList = result.data
                    articleList.datas.addAll(0,topArticleResult.data) //数据拼接
                    currentPage++
                    emit(articleList)
                }
            }.map {
                for ( (index, element)in it.datas.withIndex()) {
                  if(index<4){
                      element.isTop = true
                  }
                }
                it
            }.
            flowOn(Dispatchers.IO) //指定flow的运行线程
                .collect{//collect的运行线程需要看launch的线程
                emitArticleUiState(showLoading = false, successData = it, isRefresh=true)
            }
        }
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
        val needLogin: Boolean? = null,
        val topArticleData: List<Article>? = null
    )

}

