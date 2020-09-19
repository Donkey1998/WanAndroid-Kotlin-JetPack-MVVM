package com.wanandroid.customui.imgedittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText
import com.wanandroid.R

/**
 * Created by Donkey
 * on 2020/9/17
 */
@SuppressLint("AppCompatCustomView")
class ImgEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.editTextStyle
) :
    EditText(context, attrs, defStyle), OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null
    private var hasFoucs = false
    private var imgEditTextListener :ImgEditTextListener? = null
    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,2是获得右边的图片  顺序是左上右下（0,1,2,3,）
        mClearDrawable = compoundDrawables[2]
        @SuppressLint("Recycle") val ta =
            context.obtainStyledAttributes(attrs, R.styleable.ImgEditText)
        if (mClearDrawable == null) {
            mClearDrawable = ta.getDrawable(R.styleable.ImgEditText_rightImg)
        }
        mClearDrawable!!.setBounds(
            0,
            0,
            mClearDrawable!!.intrinsicWidth,
            mClearDrawable!!.intrinsicHeight
        )
        // 默认设置隐藏图标
        setClearIconVisible(false)
        // 设置焦点改变的监听
        onFocusChangeListener = this
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val touchable =
                    event.x > width - totalPaddingRight && event.x < width - paddingRight
                if (touchable) {
                    imgEditTextListener?.rightImgOnTouchListener(this.text.toString())
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun setImgEditTextOnTouchListener (imgEditTextListener :ImgEditTextListener?){
        this.imgEditTextListener = imgEditTextListener
    }
    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        hasFoucs = hasFocus
        if (hasFocus) {
            setClearIconVisible(text.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            right,
            compoundDrawables[3]
        )
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        if (hasFoucs) {
            setClearIconVisible(s.isNotEmpty())
        }
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.animation =
            shakeAnimation(
                5
            )
    }

    companion object {
        /**
         * 晃动动画
         *
         * @param counts
         * 1秒钟晃动多少下
         * @return
         */
        fun shakeAnimation(counts: Int): Animation {
            val translateAnimation: Animation = TranslateAnimation(0F, 10F, 0F, 0F)
            //设置一个循环加速器，使用传入的次数就会出现摆动的效果。
            translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
            translateAnimation.duration = 500
            return translateAnimation
        }
    }

    init {
        init(context, attrs)
    }
}