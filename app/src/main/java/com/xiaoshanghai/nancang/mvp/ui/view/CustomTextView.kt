package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class CustomTextView : AppCompatTextView {

    private var mSolidColor = Color.parseColor("#565656")
    private var mStokeColor = 0
    private var mStokeWidth = 0f
    private var mCornerRadius = 20f
    private var mPaint: Paint? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val gd = GradientDrawable()
        gd.cornerRadius = dip2px(mCornerRadius).toFloat()
        gd.setStroke(dip2px(mStokeWidth), mSolidColor)
        gd.setColor(mSolidColor)
        setBackgroundDrawable(gd)
    }

    fun setTextBgColor(color:String) {
        mSolidColor = Color.parseColor(color)
        initView()
    }

   private fun dip2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}