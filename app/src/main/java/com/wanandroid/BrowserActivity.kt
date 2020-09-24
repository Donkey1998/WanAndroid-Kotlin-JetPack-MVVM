package com.wanandroid

import android.graphics.Bitmap
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.wanandroid.base.BaseActivity
import com.wanandroid.ui.readhistory.ReadHistoryViewModel
import com.wanandroid.util.Share
import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.dialog_share.*
import kotlinx.android.synthetic.main.title_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Lu
 * on 2018/3/25 21:47
 */
class BrowserActivity : BaseActivity() {
    private val readHistoryViewModel by viewModel<ReadHistoryViewModel>()
    companion object {
        const val URL = "url"
        const val TITLE = "title"
    }
    private val bottomSheetDialog by lazy { BottomSheetDialog(this) }

    override fun getLayoutResId() = R.layout.activity_browser

    override fun initView() {
        mToolbar.title = getString(R.string.is_loading)
        mToolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(mToolbar);
        initWebView()
        initShareDialog()
        readHistoryViewModel.addHistory(intent?.extras?.getString(URL).toString(), Html.fromHtml(intent?.extras?.getString(TITLE)).toString() )
    }

    private fun initShareDialog() {
        Share.URL = intent?.extras?.getString(URL).toString()
        Share.Title = Html.fromHtml(intent?.extras?.getString(TITLE)).toString()

        bottomSheetDialog.run {
            setContentView(R.layout.dialog_share)
            qqImageButton.setOnClickListener {
                Share.shareToQQ()
                dismiss()
            }
            wxImageButton.setOnClickListener {
                Share.shareToWX()
                dismiss()
            }
            linkImageButton.setOnClickListener {
                Share.copyLink()
                dismiss()
            }
            browserImageButton.setOnClickListener {
                Share.startBrowser()
                dismiss()
            }
            moreImageButton.setOnClickListener {
                Share.moreShare()
                dismiss()
            }
            cancel_button.setOnClickListener {
                dismiss()
            }

        }


    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }

        intent?.extras?.getString(URL).let {
            if(it!!.startsWith("http:")||it.startsWith("https:")){
                //对http或者https协议的链接进行加载
                webView!!.loadUrl(it)
            }
        }
    }

    private fun initWebView() {
        progressBar.progressDrawable = this.resources
                .getDrawable(R.drawable.color_progressbar)
        webView.run {
            webViewClient = object : WebViewClient() {
                // shouldOverrideUrlLoading返回值的处理：https://blog.csdn.net/cui130/article/details/85569426
                override fun shouldOverrideUrlLoading(view: WebView?,url: String?): Boolean {
                    return if(url!!.startsWith("http:")||url.startsWith("https:")){
                        Share.URL = url.toString()
                        //对http或者https协议的链接进行加载
                        view!!.loadUrl(url)
                        false // 若设置WebViewClient 且 return false： 就会使用我们自己的webView来打开url
                    }else{
                        true //  若设置WebViewClient 且 return true： 表示当前url即使是重定向url也不会再执行（除了在return true之前使用webView.loadUrl(url)除外，因为这个会重新加载）
                    }
                }

                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
                    mToolbar.title = getString(R.string.is_loading)
                    progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(p0: WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    progressBar.visibility = View.GONE
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    super.onProgressChanged(p0, p1)
                    progressBar.progress = p1
                    Log.e("browser1", p1.toString())
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    Log.e("browser2", p1.toString())
                    p1?.let {
                        if(p1.indexOf("http") == -1){  //p1有时候会返回url链接
                            mToolbar.title = p1
                            Share.Title= Html.fromHtml("$p1:  ").toString()
                        }
                    }
                }

            }
        }
        val webSetting: WebSettings = webView.getSettings()
        webSetting.setSupportMultipleWindows(false)
        webSetting.domStorageEnabled = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }
     override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Share ->
                bottomSheetDialog.show()
            else ->
                super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        if (webView != null) webView.destroy()
        super.onDestroy()
    }

}