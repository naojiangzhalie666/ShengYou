package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.LotteryEntity
import com.xiaoshanghai.nancang.utils.UserManagerUtils

class LotteryBarrage : ConstraintLayout {

    @BindView(R.id.cons_bg)
    lateinit var mConsBg: ConstraintLayout

    @BindView(R.id.tv_user_name)
    lateinit var mTvUserName: TextView

    @BindView(R.id.tv_bx_name)
    lateinit var mTvBxName: TextView

    @BindView(R.id.tv_result)
    lateinit var mTvResult: TextView

    private var mView: View

    private var mContext: Context

    private var mLotterEntity: LotteryEntity? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context!!
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_lottery_barrage, this, true)
        ButterKnife.bind(mView!!)

    }

    fun setLotterResult(lotteryEntity: LotteryEntity?) {
        this.mLotterEntity = lotteryEntity
        if (mLotterEntity == null) return

        mConsBg.setBackgroundResource(UserManagerUtils.getNobleBg(lotteryEntity?.userNoble.toString()))

        mTvUserName.text = mLotterEntity?.userName

        var startColor = UserManagerUtils.getNobleStartColor(mLotterEntity?.userNoble.toString())
        var endColor = UserManagerUtils.getNobleEndColor(mLotterEntity?.userNoble.toString())
        setTextViewStyles(mTvUserName,startColor,endColor)

        if (mLotterEntity?.boxType == 1) {
            mTvBxName.text = "白银宝箱"
            mTvBxName.setTextColor(ContextCompat.getColor(mContext,R.color.color_e3f4ff))
            mTvResult.setTextColor(ContextCompat.getColor(mContext,R.color.color_e3f4ff))
        } else if (mLotterEntity?.boxType == 2){
            mTvBxName.text = "黄金宝箱"
            mTvBxName.setTextColor(ContextCompat.getColor(mContext,R.color.color_fff714))
            mTvResult.setTextColor(ContextCompat.getColor(mContext,R.color.color_fff714))
        }

        mTvResult.text = mLotterEntity?.lotteryResult

    }

    private fun setTextViewStyles(textView: TextView, startColor: String, endColor: String) {
        val mLinearGradient = LinearGradient(0f, 0f, textView.paint.textSize * textView.text.length, 0f, Color.parseColor(startColor), Color.parseColor(endColor), Shader.TileMode.CLAMP)
        textView.paint.shader = mLinearGradient
        textView.invalidate()
    }
}