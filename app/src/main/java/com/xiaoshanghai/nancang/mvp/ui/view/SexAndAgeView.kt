package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R

class SexAndAgeView : RelativeLayout {

    @BindView(R.id.constrain)
    lateinit var mRl: ConstraintLayout

    @BindView(R.id.iv_sex)
    lateinit var mIvSex: ImageView

    @BindView(R.id.tv_age)
    lateinit var mTvAge: TextView

    private lateinit var mContext: Context

    private lateinit var mView: View

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleRes: Int) : super(context, attrs, defStyleRes) {
        init(context)
    }

    private fun init(context: Context?) {
        this.mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_sex_and_age, this, true)
        ButterKnife.bind(mView)
    }

    fun setSexAndAge(sex: Int, age: String) {
        if (sex == 0) {
            mRl.setBackgroundResource(R.drawable.shape_pink_r7_bg)
            mIvSex.setImageResource(R.mipmap.icon_sex_nv)
        } else if (sex == 1) {
            mRl.setBackgroundResource(R.drawable.shape_b_r7_bg)
            mIvSex.setImageResource(R.mipmap.icon_sex_nan)
        }

        mTvAge.text = age
    }
}