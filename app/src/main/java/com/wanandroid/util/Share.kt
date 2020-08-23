package com.wanandroid.util

import android.content.*
import android.content.pm.PackageInfo
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.wanandroid.App


/**
 * Created by Donkey
 * on 2020/8/22
 */
class Share {
    companion object {

        //分享文本 到QQ好友（微信，朋友圈同理,这里分享文本不涉及访问文件就不用判断安卓是否大于7.0了）
        fun shareToQQ(text: String? = "") {
            if (!isInstallQQ(App.getContext())) {
                Toast.makeText(App.getContext(),"您没有安装QQ", Toast.LENGTH_SHORT).show();
                return;
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                val componentName =  ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity")
                component = componentName
                type="text/*"
                putExtra(Intent.EXTRA_TEXT, text)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            if (intent.resolveActivity(App.getContext().packageManager) != null) {
                startActivity(App.getContext(), intent, null);
            }

        }

        fun shareToWX(text :String? = "") {
            if (!isInstallWeChart(App.getContext())) {
                Toast.makeText(App.getContext(),"您没有安装微信", Toast.LENGTH_SHORT).show();
                return;
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                val componentName = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
                component = componentName
                type="text/*"
                putExtra(Intent.EXTRA_TEXT, text)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            if (intent.resolveActivity(App.getContext().packageManager) != null) {
                startActivity(App.getContext(), intent, null);
            }

        }

        /*
         复制到剪切板
         */
        fun copyLink(text :String? = ""){
            //将数据转换为ClipData类
            val str:ClipData=ClipData.newPlainText("Label",text)
            //构造一个ClipboardManager类，也就是剪切板管理器类
            val cm:ClipboardManager= App.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            //最后将数据复制到系统剪切板上
            cm.setPrimaryClip(str)
            Toast.makeText(App.getContext(),"已复制到剪切板", Toast.LENGTH_SHORT).show();
        }

        fun startBrowser(text :String? = ""){
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(text)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(App.getContext(), intent, null)
        }

        fun moreShare(text :String? = ""){
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type="text/*"
                putExtra(Intent.EXTRA_TEXT, text)

            }
            val intent = Intent.createChooser(shareIntent, "分享").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(App.getContext(), intent, null)
        }




        /**检查是否安装QQ
         * @param context
         * @return
         */
        private fun isInstallQQ(context: Context): Boolean {
            var packageInfo: PackageInfo? = null
            try {
                packageInfo = context.packageManager.getPackageInfo("com.tencent.mobileqq", 0)
            } catch (e: java.lang.Exception) {
                packageInfo = null
                e.printStackTrace()
            }
            return packageInfo != null
        }


        /**检查是否安装微信
         * @param context
         * @return
         */
        private fun isInstallWeChart(context: Context): Boolean {
            var packageInfo: PackageInfo? = null
            try {
                packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mm", 0)
            } catch (e: Exception) {
                packageInfo = null
                e.printStackTrace()
            }
            return packageInfo != null
        }

    }




}