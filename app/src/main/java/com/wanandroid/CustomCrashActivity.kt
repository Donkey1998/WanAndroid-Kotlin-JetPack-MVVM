package com.wanandroid

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_custom_crash.*

/**
 * Created by Donkey
 * on 2020/9/15
 */
@Suppress("PLUGIN_WARNING")
class CustomCrashActivity :BaseActivity() {
    override fun getLayoutResId()=R.layout.activity_custom_crash
    private var mConfig: CaocConfig? = null
    private var mDialog: AlertDialog? = null
    private var errorInformation: String? = null
    override fun initView() {
        //获得配置信息,比如设置的程序崩溃显示的页面和重新启动显示的页面等等信息
        mConfig = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (mConfig == null) {
            // 这种情况永远不会发生，只要完成该活动就可以避免递归崩溃
            finish()
        }
        errorInformation=CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@CustomCrashActivity, intent)// 获取所有的错误提示
        btn_crash_restart.setOnClickListener {
            //重新打开app
            CustomActivityOnCrash.restartApplication(this@CustomCrashActivity, mConfig!!)
        }
        btn_crash_log.setOnClickListener {
            if (mDialog == null) {
                mDialog = AlertDialog.Builder(this@CustomCrashActivity)
                    .setTitle("错误详情")
                    .setMessage(errorInformation)
                    .setPositiveButton("关闭", null)
                    .setNeutralButton("复制日志") { _, _ -> copyErrorToClipboard() }
                    .create()
            }
            mDialog!!.show()
            val textView: TextView = mDialog!!.findViewById(android.R.id.message)!!
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        }
    }

    /**
     * 复制报错信息到剪贴板
     */
    private fun copyErrorToClipboard() {
        (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
            ClipData.newPlainText("错误信息", errorInformation)
        )
    }

    override fun initData() {
    }
}