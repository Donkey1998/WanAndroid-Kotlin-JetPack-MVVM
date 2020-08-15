package com.wanandroid
import com.wanandroid.ui.first.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
/**
 * Created by Donkey
 * on 2020/8/11
 */
val viewModelModule = module {
    viewModel { ArticleViewModel() }
}

val repositoryModule = module {

}

val appModule = listOf(viewModelModule, repositoryModule)