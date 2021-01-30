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
import com.xiaoshanghai.nancang.net.bean.GlobalMsgResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView


class RoomLotteryView : ConstraintLayout {

    @BindView(R.id.civ_user_avatar)
    lateinit var mCivAvatar: CircleImageView

    @BindView(R.id.tv_user_name)
    lateinit var mTvUserName: TextView

    @BindView(R.id.tv_gift_price)
    lateinit var mTvGiftPrice: TextView

    @BindView(R.id.tv_gift_name)
    lateinit var mTvGiftName: TextView

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
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_room_lottery_notice, this, true)
        ButterKnife.bind(mView!!)

        val typeface = Typeface.createFromAsset(mContext?.assets, "font/CustomFont.otf")
        mTvGiftNum.typeface = typeface


    }

    fun setNoticeResult(result: GlobalMsgResult?) {
        this.mResult = result

        if (result == null) return

        GlideAppUtil.loadImage(mContext, mResult?.sendUserAvatar, mCivAvatar, R.mipmap.icon_default_avatar)

        mTvUserName.text = mResult?.sendUserName

        mTvGiftPrice.text = mResult?.giftPrice

        mTvGiftName.text = mResult?.giftName

        GlideAppUtil.loadImage(mContext, mResult?.giftStaticUrl, mIvGift)

        mTvGiftNum.text = "x${mResult?.giftNum}"

    }

    fun setViewType(type: Int) {
        mViewType = type
    }

    fun getViewType():Int{
        return mViewType
    }

//    private fun getPrice(giftPrice: String?, giftNum: String?): Int {
//        var mGiftPrice = giftPrice?.toFloat()
//        var mGiftNum = giftNum?.toFloat()
//        return if (mGiftPrice == null || mGiftNum == null) {
//            0
//        } else {
//            (mGiftPrice * mGiftNum).toInt()
//        }
//    }

}