package com.wanandroid.customui.searchtitle

/**
 * Created by Donkey
 * on 2020/9/11
 */
interface SearchTitleListener {
    fun leftButtonOnclick()
    fun search(searchStr: String?)
    fun rightButton(searchStr: String?)
    fun clearButton()
}