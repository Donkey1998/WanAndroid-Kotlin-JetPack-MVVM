package com.wanandroid.ui.home

import androidx.fragment.app.Fragment
import com.wanandroid.R
import com.wanandroid.base.BaseFragment
import com.wanandroid.ui.square.SquareFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.wanandroid.ui.first.FirstFragment
import com.wanandroid.ui.guide.GuideFragment
import com.wanandroid.ui.lastedProject.LastedProjectFragment
import com.wanandroid.ui.system.SystemFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Donkey
 * on 3:18 PM
 */
class HomeFragment : BaseFragment() {
    private val titleList  = arrayListOf("首页","导航","项目", "广场", "体系")
    private val fragmentList = arrayListOf<Fragment>()
    private val firstFragment by lazy { FirstFragment() } //首页
    private val guideFragment by lazy { GuideFragment() } //导航
    private val lastedProjectFragment by lazy { LastedProjectFragment() } // 最新项目
    private val squareFragment by lazy { SquareFragment() } // 广场
    private val systemFragment by lazy { SystemFragment() } // 体系
    private var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    init {
        fragmentList.add(firstFragment)
        fragmentList.add(guideFragment)
        fragmentList.add(lastedProjectFragment)
        fragmentList.add(squareFragment)
        fragmentList.add(systemFragment)
    }

    override fun getLayoutResId() = R.layout.fragment_home

    private val  TAG : String = HomeFragment::class.java.simpleName
    override fun initView() {
        initViewPager()
    }


    private fun initViewPager() {
        viewPager.offscreenPageLimit = 1
        viewPager.adapter =  object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = titleList.size
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (mOnPageChangeCallback == null) mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

            }
        }
        mOnPageChangeCallback?.let { viewPager.registerOnPageChangeCallback(it) }
    }

    override fun onStop() {
        super.onStop()
        mOnPageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }
    }

    override fun initData() {
    }
}