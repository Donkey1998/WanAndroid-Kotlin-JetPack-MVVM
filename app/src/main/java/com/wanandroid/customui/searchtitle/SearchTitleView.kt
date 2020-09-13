package com.wanandroid.customui.searchtitle

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import com.wanandroid.R
import kotlinx.android.synthetic.main.title_search.view.*


/**
 * Created by Donkey
 * on 2020/9/11
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SearchTitleView :LinearLayout {
    private var backBt: ImageButton? = null
    private var clearBt: ImageButton? = null
    private var searchBt: ImageButton? = null
    private var searchEditText: EditText? = null
    var onSearchTitleListener: SearchTitleListener? = null
    var hint: CharSequence? = null
        set(value) {
            field = value
            searchEditText?.hint = value
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init(context)
        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SearchTitleView)
            hint = typedArray.getText(R.styleable.SearchTitleView_hint)
            typedArray.recycle()
        }
    }

    private fun init(context: Context){
        val view = LayoutInflater.from(context).inflate(R.layout.title_search,this)
        backBt = view.findViewById(R.id.back_bt)
        clearBt = view.findViewById(R.id.clear_bt)
        searchBt = view.findViewById(R.id.search_bt)
        searchEditText = view.findViewById(R.id.search_edt)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //  默认打开软键盘
        postDelayed({
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT); //显示软键盘
        }, 100)

        searchEditText?.let {
                requestFocus() //Android9.0系统如果控件需要实现光标，则需要手动调取xxx 的requestFocus()方法
             it.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(text: CharSequence,start: Int,count: Int, after: Int) {
                 //输入文字前触发
             }
             override fun onTextChanged(text: CharSequence,start: Int,before: Int,count: Int) {
                 //text改变过程中，一般在此加入监听事件。
                 if(text.isEmpty()){ //为空时不显示clearBt
                     clearBt?.visibility = View.INVISIBLE
                 }else{
                     clearBt?.visibility = View.VISIBLE
                 }

             }
             override fun afterTextChanged(text: Editable) {
                 //输入后触发
             }
         })
             // 监听软键盘的按键
             it. setOnEditorActionListener { _, actionId, event -> //回车等操作
                 if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || event != null && KeyEvent.KEYCODE_ENTER === event.keyCode && KeyEvent.ACTION_DOWN === event.action
                 ) {
                     // 关闭软键盘
                     imm.hideSoftInputFromWindow(searchEditText!!.windowToken, 0);
                     // 搜索
                     onSearchTitleListener?.search(it.text.toString())
                 }
                 true
             }
         }
         clearBt?.setOnClickListener {
             searchEditText?.setText("")
             onSearchTitleListener?.clearButton()
         }
         backBt?.setOnClickListener {
             onSearchTitleListener?.leftButtonOnclick()
         }
         searchBt?.setOnClickListener {
             onSearchTitleListener?.rightButton(search_edt.text.toString())
         }
    }

     fun setSearchTitleListener(onSearchTitleListener: SearchTitleListener?) {
        this.onSearchTitleListener = onSearchTitleListener
    }

    fun getSearchEditText() : EditText? {
        return searchEditText
    }

}