package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.net.bean.GlobalMsgResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView


class RoomNoticeView : ConstraintLayout {

    @BindView(R.id.iv_bg)
    lateinit var mBg: ImageView

    @BindView(R.id.civ_giver_avarar)
    lateinit var mSendAvatar: CircleImageView

    @BindView(R.id.tv_give_name)
    lateinit var mTvSnedName: TextView

    @BindView(R.id.civ_donor_avarar)
    lateinit var mGetAvatar: CircleImageView

    @BindView(R.id.tv_donor_name)
    lateinit var mTvGetName: TextView

    @BindView(R.id.iv_gift)
    lateinit var mIvGift: ImageView

    @BindView(R.id.tv_gift_num)
    lateinit var mTvGiftNum: TextView

    private var mViewType: Int = -1

    private var mContext: Context? = null

    private var mView: View? = null

    private var mResult: GlobalMsgResult? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_room_notice, this, true)
        ButterKnife.bind(mView!!)

        val typeface = Typeface.createFromAsset(mContext?.assets, "font/CustomFont.otf")
        mTvGiftNum.typeface = typeface

    }

    fun setNoticeResult(result: GlobalMsgResult?) {
        if (result == null) return
        this.mResult = result

        var giftPrice = mResult?.giftPrice
        var giftNum = mResult?.giftNum
        var price = getPrice(giftPrice, giftNum)
        if (price >= Constant.PUBLICITY_PRICE_3 && price < Constant.PUBLICITY_PRICE_4) {
            mBg.setImageResource(R.mipmap.img_send_gift)
        } else if (price >= Constant.PUBLICITY_PRICE_4) {
            mBg.setImageResource(R.mipmap.img_room_notice_bg)

        }

        GlideAppUtil.loadImage(mContext, mResult?.sendUserAvatar, mSendAvatar, R.mipmap.icon_default_avatar)
        mTvSnedName.text = mResult?.sendUserName

        GlideAppUtil.loadImage(mContext, mResult?.getUserAvatar, mGetAvatar, R.mipmap.icon_default_avatar)
        mTvGetName.text = mResult?.getUserName

        GlideAppUtil.loadImage(mContext, mResult?.giftStaticUrl, mIvGift)

        mTvGiftNum.text = "x${mResult?.giftNum}"
    }

    private fun getPrice(giftPrice: String?, giftNum: String?): Int {
        var mGiftPrice = giftPrice?.toFloat()
        var mGiftNum = giftNum?.toFloat()
        return if (mGiftPrice == null || mGiftNum == null) {
            0
        } else {
            (mGiftPrice * mGiftNum).toInt()
        }
    }

    fun setViewType(type: Int) {
        mViewType = type
    }

    fun getViewType():Int{
        return mViewType
    }

}