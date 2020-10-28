package com.wanandroid.customui.builddialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wanandroid.R
import com.wanandroid.customui.commondialog.CommonDialogListener

/**
 * Created by Donkey
 * on 2020/10/28
 */
class BuildDialog (context: Context?, themeResId: Int) : Dialog(context!!, themeResId) {
    class Builder(private val context: Context){
        private  var  titleStr: String? = null
        private  var  contentStr: String? = null
        private  var  cancelStr: String? = null
        private  var  confirmStr: String? = null
        private  var   mCommonDialogListener : BuildDialogListener? = null

        fun setTitleText(title: String): Builder {
            this.titleStr = title
            return this
        }

        fun setContentText(content: String): Builder{
            this.contentStr = content
            return this
        }

        fun setCancelText(cancel: String): Builder{
            this.cancelStr = cancel
            return this
        }

        fun setConfirmText(confirm: String): Builder{
            this.confirmStr = confirm
            return this
        }

        fun setDialogOnClickListener( commonDialogListener : BuildDialogListener): Builder{
            this.mCommonDialogListener = commonDialogListener
            return this
        }

        fun create():BuildDialog{
//            自定义的样式初始化Dialog
            val dialog = BuildDialog(context, R.style.dialog)
//            初始化Dialog的布局页面
            val dialogLayoutView = LayoutInflater.from(context).inflate(R.layout.dialog_common,
                null)
            val dialogTitle: TextView = dialogLayoutView.findViewById(R.id.dialog_title)
            val dialogContent:TextView = dialogLayoutView.findViewById(R.id.dialog_content)
            val dialogCancel:TextView = dialogLayoutView.findViewById(R.id.dialog_cancel)
            val dialogConfirm:TextView = dialogLayoutView.findViewById(R.id.dialog_confirm)
            dialogCancel.setOnClickListener {
                mCommonDialogListener?.cancelOnClickListener()
            }
            dialogConfirm.setOnClickListener {
                mCommonDialogListener?.confirmOnClickListener()
            }
            if(titleStr.isNullOrEmpty()){
                dialogTitle.visibility = View.GONE
            }else{
                dialogTitle.text = titleStr
            }
            if(contentStr.isNullOrEmpty()){
                dialogContent.visibility = View.GONE
            }else{
                dialogContent.text = contentStr
            }
            if(cancelStr.isNullOrEmpty()){
                dialogCancel.visibility  = View.GONE
            }else{
                dialogCancel.text = cancelStr
            }
            if(confirmStr.isNullOrEmpty()){
                dialogConfirm.visibility = View.GONE
            }else{
                dialogConfirm.text = confirmStr
            }
//            将初始化完整的布局添加到dialog中
            dialog.setContentView(dialogLayoutView)
            return dialog
        }

    }
}

