package com.wanandroid
import com.wanandroid.ui.blog.BlogViewModel
import com.wanandroid.ui.first.ArticleViewModel
import com.wanandroid.ui.guide.GuideViewModel
import com.wanandroid.ui.login.RegisterLoginViewModel
import com.wanandroid.ui.searchhistory.SearchHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
/**
 * Created by Donkey
 * on 2020/8/11
 */
val viewModelModule = module {
    viewModel { ArticleViewModel() }
    viewModel { GuideViewModel() }
    viewModel { BlogViewModel() }
    viewModel { SearchHistoryViewModel() }
    viewModel { RegisterLoginViewModel() }
}

val repositoryModule = module {

}

val appModule = listOf(viewModelModule, repositoryModule)