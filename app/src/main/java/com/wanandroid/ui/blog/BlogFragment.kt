package com.wanandroid.ui.blog

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wanandroid.R
import com.wanandroid.base.BaseFragment
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentBlogBinding
import com.wanandroid.model.resultbean.BlogType
import kotlinx.android.synthetic.main.fragment_blog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class BlogFragment : BaseVMFragment<FragmentBlogBinding>(R.layout.fragment_blog) {
    private val blogViewModel by viewModel<BlogViewModel>()
    private val blogTypeList = mutableListOf<BlogType>()
    override fun initView() {
        blogViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = blogTypeList.size

            override fun createFragment(position: Int) = chooseFragment(position)

        }
        TabLayoutMediator(blogTabLayout, blogViewPager) { tab, position ->
            tab.text = blogTypeList[position].name
        }.attach()
    }

    private fun chooseFragment(position: Int): Fragment {
      return  BlogArticleFragment.newInstance(blogTypeList[position].id)
    }

    override fun initData() {
        blogViewModel.getBlogType()
    }


    override fun startObserve() {
        blogViewModel.blogTypeList.observe(viewLifecycleOwner, Observer {
            it.run {
                blogTypeList.clear()
                blogTypeList.addAll(it)
                blogViewPager.adapter?.notifyDataSetChanged()
            }
        })
    }

}