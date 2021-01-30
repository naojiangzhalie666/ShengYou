package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.GlobalMsgResult


class NewRoomLotteryView : ConstraintLayout {

    @BindView(R.id.tv_name)
    lateinit var mTvName: TextView

    @BindView(R.id.tv_type)
    lateinit var mTvType: TextView

    @BindView(R.id.tv_gift_name)
    lateinit var mTvGiftName: TextView

    @BindView(R.id.tv_gift_num)
    lateinit var mTvGiftNum: TextView

    @BindView(R.id.tv_price)
    lateinit var mTvPrice: TextView


    private var mViewType: Int = -1

    private var mContext: Context? = null

    private var mView: View? = null

    private var mResult: GlobalMsgResult? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_new_room_lottery_notice, this, true)
        ButterKnife.bind(mView!!)

    }

    fun setNoticeResult(result: GlobalMsgResult?) {
        this.mResult = result

        if (result == null) return


        mTvName.text = mResult?.sendUserName

        if (result?.boxType == 1) {
            mTvType.text = "打开白银宝箱"
        } else if (result?.boxType == 2) {
            mTvType.text = "打开黄金宝箱"
        }

        mTvPrice?.text = "价值${result?.giftPrice}"

        mTvGiftName.text = mResult?.giftName


        mTvGiftNum.text = "x${mResult?.giftNum}"

    }

    fun setViewType(type: Int) {
        mViewType = type
    }

    fun getViewType(): Int {
        return mViewType
    }


}