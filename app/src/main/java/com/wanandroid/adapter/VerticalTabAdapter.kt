package com.wanandroid.adapter

import android.graphics.Color
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.ITabView.TabTitle


/**
 * Created by Donkey
 * on 2020/8/19
 */
class VerticalTabAdapter(private val titles: List<String>) :TabAdapter {
    override fun getIcon(position: Int) = null

    override fun getBadge(position: Int) = null

    override fun getBackground(position: Int) = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return TabTitle.Builder()
            .setContent(titles[position]) //设置数据
            .setTextColor(Color.parseColor("#2196F3"), Color.parseColor("#000000"))
            .build()
    }

    override fun getCount() = titles.size
}