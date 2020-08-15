package com.wanandroid.ui.first

import android.util.Log
import androidx.lifecycle.Observer
import com.wanandroid.R
import com.wanandroid.base.BaseVMFragment
import com.wanandroid.databinding.FragmentFirstBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Donkey
 * on 3:18 PM
 */
class FirstFragment : BaseVMFragment<FragmentFirstBinding>(R.layout.fragment_first) {
    private val articleViewModel by viewModel<ArticleViewModel>() //koin
    override fun initView() {
        binding.run {
         viewModel = articleViewModel
        }
    }

    override fun initData() {

    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
                    Log.d("uiState", it.toString())
        })
    }
}