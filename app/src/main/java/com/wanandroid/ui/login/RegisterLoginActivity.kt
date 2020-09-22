package com.wanandroid.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wanandroid.App
import com.wanandroid.R
import com.wanandroid.base.BaseVMActivity
import com.wanandroid.customui.imgedittext.ImgEditTextListener
import com.wanandroid.databinding.ActivityRegisterLoginBinding
import kotlinx.android.synthetic.main.activity_register_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterLoginActivity : BaseVMActivity() {
    private val registerLoginViewModel by viewModel<RegisterLoginViewModel>()
    private val binding by binding<ActivityRegisterLoginBinding>(R.layout.activity_register_login)
    override fun initView() {
        binding.run {
            viewModel = registerLoginViewModel
        }
        setOnClickListener()
    }

    override fun initData() {
    }

    override fun startObserve() {
        registerLoginViewModel.uiState.observe(this@RegisterLoginActivity, Observer{
            if(it.isLoading){
                showProgressDialog()
            }else{
                dismissProgressDialog()
            }

            it.successData.let {it1 ->
               if(!it1?.username.isNullOrEmpty() && it1?.id.toString().isNotEmpty()){
                    val intent = Intent()
                    intent.putExtra("userName",it1?.username)
                    intent.putExtra("userId",it1?.id.toString())
                    setResult(2, intent);
                    finish()
                }

            }
            it.showError?.let { it1 ->
                Toast.makeText(App.getContext(),it1, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setOnClickListener(){
        user_edt.setImgEditTextOnTouchListener(object : ImgEditTextListener{
            override fun rightImgOnTouchListener(text: String) {
                user_edt.setText("")
            }

        })
        login_bt.setOnClickListener {
            registerLoginViewModel.login()
        }
        goBack.setOnClickListener {
            finish()
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}