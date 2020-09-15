package com.wanandroid.ui.first

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.wanandroid.App
import com.wanandroid.BrowserActivity
import com.wanandroid.R
import com.wanandroid.adapter.FirstArticleAdapter
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.customui.MyBanner
import com.wanandroid.databinding.FragmentFirstBinding
import com.wanandroid.model.resultbean.Banner
import com.wanandroid.util.GlideImageLoader
import com.wanandroid.util.dp2px
import com.wanandroid.view.CustomLoadMoreView
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class FirstFragment() : BaseVMFragment<FragmentFirstBinding>(R.layout.fragment_first) {
    private val articleViewModel by viewModel<ArticleViewModel>()
    private val firstArticleAdapter by lazy { FirstArticleAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private val banner by lazy { MyBanner(activity) }
    override fun initView() {
        binding.run {
         viewModel = articleViewModel
         adapter= firstArticleAdapter
        }
        initBanner()
        firstArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = Bundle()
                bundle.putString(BrowserActivity.URL,firstArticleAdapter.data[position].link)
                bundle.putString(BrowserActivity.TITLE,firstArticleAdapter.data[position].title)
//                    val bundle =
//                        bundleOf(BrowserActivity.URL to firstArticleAdapter.data[position].link,
//                                BrowserActivity.TITLE to firstArticleAdapter.data[position].title
//                                )
                    NavHostFragment.findNavController(this@FirstFragment)
                        .navigate(R.id.browserActivity, bundle)
                }
            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())//添加上拉加载更多布局
            setOnLoadMoreListener({ loadMore() }, homeRecycleView) //上拉加载更多
        }

    }
    private fun loadMore() {
        articleViewModel.getFirstArticleList(false)
    }

    private fun refresh() {
        articleViewModel.getFirstArticleList(true)
        articleViewModel.getBannerList()
    }


    override fun initData() {
        refresh()
    }

    private fun initBanner() {

        banner.run {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200))
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    val bundle = Bundle()
                    bundle.putString(BrowserActivity.URL,bannerUrls[position])
                    bundle.putString(BrowserActivity.TITLE,bannerTitles[position])
                    NavHostFragment.findNavController(this@FirstFragment)
                        .navigate(R.id.browserActivity, bundle)
                }
            }
        }
    }

    private fun setBanner(bannerList: List<Banner>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        banner.setImages(bannerImages)
            .setBannerTitles(bannerTitles)
            .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            .setDelayTime(3000)
        banner.start()
    }

    override fun startObserve() {
        articleViewModel.mBanners.observe(viewLifecycleOwner, Observer { it ->
            it?.let { setBanner(it) }
        })
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            it.successData?.let { list ->
                firstArticleAdapter.run {
                    firstArticleAdapter.setEnableLoadMore(false)
                    if (it.isRefresh){
                        replaceData(list.datas)
                    }else{
                        addData(list.datas)
                    }
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) firstArticleAdapter.loadMoreEnd()

            it.showError?.let { message ->
                Toast.makeText(App.getContext(),if (message.isBlank()) "网络异常" else message,Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

}