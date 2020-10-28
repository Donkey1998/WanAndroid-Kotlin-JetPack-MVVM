package com.wanandroid.ui.profile

import android.content.Intent
import android.util.Log
import com.wanandroid.App
import com.wanandroid.R
import com.wanandroid.base.BaseFragment
import com.wanandroid.customui.commondialog.CommonDialog
import com.wanandroid.customui.commondialog.CommonDialogListener
import com.wanandroid.ui.login.RegisterLoginActivity
import com.wanandroid.ui.readhistory.ReadHistoryActivity
import com.wanandroid.util.SharedPreferencesData
import kotlinx.android.synthetic.main.fragment_profile.*


/**
 * Created by Donkey
 * on 3:18 PM
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class ProfileFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_profile

    override fun initView() {
        setOnClickListener()
//        login.isEnabled = !SharedPreferencesData.isLogin
        if(SharedPreferencesData.isLogin){
            loginRegister.text = SharedPreferencesData.name
        }else{
            loginRegister.text = resources.getString(R.string.loginRegister)
        }
    }

    override fun initData() {

    }

    private fun setOnClickListener(){
        login.setOnClickListener {
            val intent = Intent(App.getContext(), RegisterLoginActivity::class.java)
            startActivityForResult(intent, 1) // requestCode -> 666
        }
        header.setOnClickListener {
            Log.d("ProfileFragment", "header")
        }
        readHistory.setOnClickListener {
            Log.d("ProfileFragment", "readHistory")
            val intent = Intent(App.getContext(), ReadHistoryActivity::class.java)
            startActivity(intent)
        }
        clearCache.setOnClickListener {
            Log.d("ProfileFragment", "clearCache")
            activity?.let {
                val mDialog =
                    CommonDialog(it)
                mDialog.setViewText("标题","","取消","确定")
                mDialog.setDialogOnClickListener(object :
                    CommonDialogListener {
                    override fun cancelOnClickListener() {
                        mDialog.dismiss()
                    }
                    override fun confirmOnClickListener() {
                    }

                })
                mDialog.show();
            }

        }
        aboutAuthor.setOnClickListener {
            Log.d("ProfileFragment", "aboutAuthor")
        }
        systemSetting.setOnClickListener {
            Log.d("ProfileFragment", "systemSetting")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === 2) {
            if (requestCode === 1) {
                loginRegister.text = SharedPreferencesData.name
//                login.isEnabled = !SharedPreferencesData.isLogin
            }
        }
    }
}

