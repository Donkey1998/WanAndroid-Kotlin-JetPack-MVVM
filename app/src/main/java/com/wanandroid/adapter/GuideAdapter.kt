package com.wanandroid.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wanandroid.BrowserActivity
import com.wanandroid.R
import com.wanandroid.model.resultbean.Article
import com.wanandroid.model.resultbean.Guide
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Created by Donkey
 * on 2020/8/19
 */
class GuideAdapter (layoutResId: Int = R.layout.item_guide) : BaseQuickAdapter<Guide, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: Guide) {
        helper.setText(R.id.guideName, item.name)
        helper.getView<TagFlowLayout>(R.id.guideTagLayout).run {
            adapter = object : TagAdapter<Article>(item.articles) {
                override fun getCount(): Int {
                    return item.articles.size
                }
                override fun getView(parent: FlowLayout, position: Int, t: Article): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                        parent, false) as TextView
                    tv.text = t.title
                    return tv
                }
            }
            setOnTagClickListener { view, position, _ ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,item.articles[position].link)
                bundle.putString(BrowserActivity.TITLE,item.articles[position].link)
                androidx.navigation.Navigation.findNavController(view)
                    .navigate(R.id.browserActivity, bundle)
                true
            }

        }
    }
}
