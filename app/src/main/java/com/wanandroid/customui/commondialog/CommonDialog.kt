package com.wanandroid.customui.commondialog


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.wanandroid.R
import kotlinx.android.synthetic.main.dialog_common.*


/**
 * Created by Donkey
 * on 2020/10/27
 */

class CommonDialog(context: Context, themeResId: Int? = R.style.dialog) : Dialog(context, themeResId!!) {
    private  var  titleStr: String? = null
    private  var  contentStr: String? = null
    private  var  cancelStr: String? = null
    private  var  confirmStr: String? = null
    private  var   mCommonDialogListener : CommonDialogListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_common)

        dialog_cancel?.setOnClickListener {
            mCommonDialogListener?.cancelOnClickListener()
        }
        dialog_confirm?.setOnClickListener {
            mCommonDialogListener?.confirmOnClickListener()
        }
        if(titleStr.isNullOrEmpty()){
            dialog_title.visibility = View.GONE
        }else{
            dialog_title.text = titleStr
        }
        if(contentStr.isNullOrEmpty()){
            dialog_content.visibility = View.GONE
        }else{
            dialog_content.text = contentStr
        }
        if(cancelStr.isNullOrEmpty()){
           dialog_cancel.visibility  = View.GONE
        }else{
            dialog_cancel.text = cancelStr
        }
        if(confirmStr.isNullOrEmpty()){
            dialog_confirm.visibility = View.GONE
        }else{
            dialog_confirm.text = confirmStr
        }
    }

    fun setViewText(title: String, content: String, cancel: String, confirm: String) {
        titleStr = title
        contentStr = content
        cancelStr = cancel
        confirmStr = confirm
    }

    fun setDialogOnClickListener( commonDialogListener : CommonDialogListener){
        mCommonDialogListener = commonDialogListener
    }
}