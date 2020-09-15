package com.wanandroid.util

import android.view.View

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}
/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun View.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}