package com.wanandroid

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.wanandroid.util.Share
import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.dialog_share.*
import kotlinx.android.synthetic.main.title_layout.*


/**
 * Created by Lu
 * on 2018/3/25 21:47
 */
class BrowserActivity : BaseActivity() {

    companion object {
        const val URL = "url"
        const val TITLE = "title"
    }
    private val bottomSheetDialog by lazy { BottomSheetDialog(this) }

    override fun getLayoutResId() = R.layout.activity_browser

    override fun initView() {
        mToolbar.title = getString(R.string.is_loading)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
        setSupportActionBar(mToolbar);
        initWebView()
        initShareDialog()
    }

    private fun initShareDialog() {
        val  text : String? = Html.fromHtml(intent?.extras?.getString(TITLE)).toString()+ ":  "+intent?.extras?.getString(URL)

        bottomSheetDialog.run {
            setContentView(R.layout.dialog_share)
            qqImageButton.setOnClickListener {
                Share.shareToQQ(text)
                dismiss()
            }
            wxImageButton.setOnClickListener {
                Share.shareToWX(text)
                dismiss()
            }
            linkImageButton.setOnClickListener {
                Share.copyLink(intent?.extras?.getString(URL))
                dismiss()
            }
            browserImageButton.setOnClickListener {
                Share.startBrowser(intent?.extras?.getString(URL))
                dismiss()
            }
            moreImageButton.setOnClickListener {
                Share.moreShare(text)
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
            webView.loadUrl(it)
        }
    }

    private fun initWebView() {
        progressBar.progressDrawable = this.resources
                .getDrawable(R.drawable.color_progressbar)
        webView.run {
            webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView?,url: String?): Boolean {
                    return false
                }

                override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                    super.onPageStarted(p0, p1, p2)
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
                    Log.e("browser", p1.toString())
                }

                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    super.onReceivedTitle(p0, p1)
                    p1?.let { mToolbar.title = p1 }
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
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

}