package com.wanandroid.binding

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by Donkey
 * on 2020/8/13
 */
@BindingAdapter("isRefresh")
fun SwipeRefreshLayout.isRefresh(isRefresh: Boolean) {
    isRefreshing = isRefresh
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.onRefresh(action: () -> Unit) {
    setOnRefreshListener { action() }
}

@BindingAdapter("htmlText")
fun bindHtmlText(view: TextView, html:String){
    view.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(html)
}

@BindingAdapter("title", "icon", "navigationClick", requireAll = false)
fun Toolbar.init(titleResId: Int, iconResId: Int, action: () -> Unit) {
    setTitle(titleResId)
    setNavigationIcon(iconResId)
    setNavigationOnClickListener { action() }
}

@BindingAdapter("adapter")
fun RecyclerView.adapter(adapter: RecyclerView.Adapter<*>) {
    setAdapter(adapter)
}