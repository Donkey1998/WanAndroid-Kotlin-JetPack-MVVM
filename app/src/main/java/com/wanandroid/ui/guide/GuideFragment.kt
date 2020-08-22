package com.wanandroid.ui.guide
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.wanandroid.R
import com.wanandroid.adapter.GuideAdapter
import com.wanandroid.adapter.VerticalTabAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentGuideBinding
import com.wanandroid.model.resultbean.Guide
import kotlinx.android.synthetic.main.fragment_guide.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 * Created by Donkey
 * on 3:18 PM
 */
class GuideFragment : BaseVMFragment<FragmentGuideBinding>(R.layout.fragment_guide) {
    private val  TAG : String = GuideFragment::class.java.simpleName
    private val guideViewModel by viewModel<GuideViewModel>()
    private val tabList = mutableListOf<Guide>()
    private val tabAdapter by lazy { VerticalTabAdapter(tabList.map { it.name }) }
    private val guideAdapter by lazy{GuideAdapter()}
    private var isTabOnclick :Boolean = true //是否是手动点击tab

    override fun initView() {
        binding.run {
            adapter= guideAdapter
        }
        initTabLayout()
    }

    override fun initData() {
        guideViewModel.getGuideList()
    }

    override fun startObserve() {
        guideViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it?.let { getTabList(it) }
        })
    }

    /**
     *  scrollToPosition 会把不在屏幕的 Item 移动到屏幕上，原来在上方的 Item 移动到 可见 Item 的第一项，在下方的移动到屏幕可见 Item 的最后一项。已经显示的 Item 不会移动。
        scrollToPositionWithOffset 会把 Item 移动到可见 Item 的第一项，即使它已经在可见 Item 之中。另外它还有 offset 参数，表示 Item 移动到第一项后跟 RecyclerView 上边界或下边界之间的距离（默认是 0） 
        链接：https://www.jianshu.com/p/374005604b60
     */
    private fun initTabLayout() {
        tabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {
            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                if(isTabOnclick){
                    val mLayoutManager = binding.guideRecycleView.layoutManager as LinearLayoutManager
                    mLayoutManager.scrollToPositionWithOffset(position,0)
                }
                 isTabOnclick = true
            }
        })

        guideRecycleView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val mLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                isTabOnclick = false
                tabLayout.setTabSelected( mLayoutManager.findFirstVisibleItemPosition())
            }
        })
    }

    private fun getTabList(guideList:List<Guide>){
        this.tabList.clear()
        this.tabList.addAll(guideList)
        tabLayout.setTabAdapter(tabAdapter)

        guideAdapter.setNewData(guideList)
    }

}