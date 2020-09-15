package com.wanandroid.customui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.youth.banner.Banner

class MyBanner : Banner {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true) //设置不拦截
        return super.dispatchTouchEvent(ev)
    }
}