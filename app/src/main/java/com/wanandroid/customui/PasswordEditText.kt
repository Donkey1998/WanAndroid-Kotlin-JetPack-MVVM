package com.wanandroid.customui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
class PasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.editTextStyle
) :
    EditText(context, attrs, defStyle), OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mCurrentDrawable: Drawable? = null
    private var mVisibleDrawable: Drawable? = null
    private var mInvisibleDrawable: Drawable? = null
    private var hasFoucs = false
    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        @SuppressLint("Recycle")
        val ta =context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText)
        mVisibleDrawable = ta.getDrawable(R.styleable.PasswordEditText_rightImgOne)
        mVisibleDrawable!!.setBounds(0,0, mVisibleDrawable!!.intrinsicWidth,mVisibleDrawable!!.intrinsicHeight)
        mInvisibleDrawable = ta.getDrawable(R.styleable.PasswordEditText_rightImgTwo)
        mInvisibleDrawable!!.setBounds(0,0, mInvisibleDrawable!!.intrinsicWidth,mInvisibleDrawable!!.intrinsicHeight)

        mCurrentDrawable = mVisibleDrawable
        // 密码不可见
        transformationMethod = PasswordTransformationMethod.getInstance()
        // 设置焦点改变的监听
        onFocusChangeListener = this
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        if(mCurrentDrawable?.isVisible!! && event.x > width - paddingRight - mCurrentDrawable!!.intrinsicWidth){
            if (event.action == MotionEvent.ACTION_UP) {

                if (mCurrentDrawable === mVisibleDrawable) {
                    mCurrentDrawable = mInvisibleDrawable
                    // 密码可见
                    transformationMethod = HideReturnsTransformationMethod.getInstance()
                    refreshDrawableStatus()
                } else if (mCurrentDrawable === mInvisibleDrawable) {
                    mCurrentDrawable = mVisibleDrawable
                    // 密码不可见
                    transformationMethod = PasswordTransformationMethod.getInstance()
                    refreshDrawableStatus()
                }
                if (text != null) {
                    setSelection(text.toString().length)
                }
            }
            return true
        }

        return super.onTouchEvent(event)
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        hasFoucs = hasFocus
        if (hasFocus) {
            setDrawableVisible(text.isNotEmpty())
        } else {
            setDrawableVisible(false)
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private  fun setDrawableVisible(visible: Boolean): Unit {
        if (mCurrentDrawable?.isVisible == visible) {
            return
        }
        mCurrentDrawable?.setVisible(visible, false)
        val drawables =compoundDrawablesRelative
        setCompoundDrawablesRelative(
            drawables[0],
            drawables[1],
            if (visible) mCurrentDrawable else null,
            drawables[3]
        )
    }

    private fun refreshDrawableStatus() {
        val drawables =
            compoundDrawablesRelative
        setCompoundDrawablesRelative(
            drawables[0],
            drawables[1],
            mCurrentDrawable,
            drawables[3]
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
            setDrawableVisible(s.isNotEmpty())
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