package com.wanandroid.model.resultbean

import java.io.Serializable

/**
 * Created by Donkey
 * on 2020/8/13
 */
data class ArticleList( val offset: Int,
                        val size: Int,
                        val total: Int,
                        val pageCount: Int,
                        val curPage: Int,
                        val over: Boolean,
                        val datas: MutableList<Article>
                        ): Serializable