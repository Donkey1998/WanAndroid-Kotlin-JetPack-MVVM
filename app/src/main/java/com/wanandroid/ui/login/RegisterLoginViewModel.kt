package com.wanandroid.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wanandroid.base.BaseViewModel
import com.wanandroid.model.http.ResponseResult
import com.wanandroid.model.repository.LoginRepository
import com.wanandroid.model.resultbean.Article
import com.wanandroid.model.resultbean.ArticleList
import com.wanandroid.model.resultbean.User
import com.wanandroid.ui.first.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Donkey
 * on 2020/9/19
 */
class RegisterLoginViewModel: BaseViewModel() {
    private val loginRepository = LoginRepository()
    val userName = ObservableField<String>("Donkey")
    val passWord = ObservableField<String>("SHIJIE520")
    private var _uiState = MutableLiveData<LoginUiModel>()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    fun onNameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        userName.set(s.toString())
    }

    fun onPwdChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        passWord.set(s.toString())
    }

    fun login(){
        viewModelScope.launch(Dispatchers.Main) {
            emitLoginModel(isLoading = true)
            val result = withContext(Dispatchers.IO){
                loginRepository.login(userName.get()?:"", passWord.get()?:"")
            }
            if (result is ResponseResult.Success){
                Log.d("RegisterLoginViewModel1", result.data.toString())
                val user = result.data
                emitLoginModel(isLoading = false ,successData= user,isLogin=true)
            }else if (result is ResponseResult.Error) {
                emitLoginModel(isLoading = false,showError=result.errorMsg,isLogin=true)
            }
        }
    }

    private fun emitLoginModel(
         isLoading: Boolean =false,
         showError: String? =null,
         successData: User? = null,
         isLogin: Boolean? = null) {
        val uiModel =LoginUiModel(isLoading,showError,successData,isLogin )
        _uiState.value = uiModel
    }
    data class LoginUiModel(
        val isLoading: Boolean = false,
        val showError: String? = null,
        val successData: User?,
        val isLogin: Boolean? = null
    )
}