package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.SendGift
import com.xiaoshanghai.nancang.utils.GlideAppUtil

class RoomSendBarrage : RelativeLayout {

    @BindView(R.id.rl_bg)
    lateinit var mRlBg: RelativeLayout

    @BindView(R.id.tv_user_name)
    lateinit var mTvUserName: TextView

    @BindView(R.id.tv_receive)
    lateinit var mTvReceive: TextView

    @BindView(R.id.iv_gift)
    lateinit var mIvGift: ImageView

    @BindView(R.id.tv_gift_num)
    lateinit var mTvGiftNum: TextView


    lateinit var mView: View

    lateinit var mContext: Context

    constructor(context: Context) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_room_send_gift, this, true)
        ButterKnife.bind(mView)
    }

    fun setMsgView(sendGift: SendGift?) {

        setNameColor(null, sendGift?.userNoble, mRlBg, true)

        mTvUserName.text = sendGift?.userName
        mTvReceive.text = sendGift?.handserName

        setNameColor(mTvUserName, sendGift?.userNoble, null, false)
        setNameColor(mTvReceive, sendGift?.handserNoble, null, false)

        GlideAppUtil.loadImage(mContext, sendGift?.giftImage, mIvGift)

        mTvGiftNum.text = "x" + sendGift?.giftNum.toString()


    }

    private fun setNameColor(name: TextView?, noble: String?, bg: RelativeLayout?, isBg: Boolean) {
        when (noble) {

            "0" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                } else {
                    setTextViewStyles(name, "#BBBBBB", "#BBBBBB")
                }
            }

            "1" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                } else {
                    setTextViewStyles(name, "#C6F1F1", "#C6F1F1")
                }
            }

            "2" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                } else {
                    setTextViewStyles(name, "#FFD3A7", "#FFD3A7")
                }
            }

            "3" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                } else {
                    setTextViewStyles(name, "#ED6C44", "#FFB230")
                }
            }

            "4" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_4)
                } else {
                    setTextViewStyles(name, "#06B4FD", "#6CF3FF")
                }
            }

            "5" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_5)
                } else {
                    setTextViewStyles(name, "#FF57CE", "#B735F3")
                }
            }

            "6" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_6)
                } else {
                    setTextViewStyles(name, "#FFFF00", "#FF6E02")
                }
            }

            "7" -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_7)
                } else {
                    setTextViewStyles(name, "#F00000", "#FC726D")
                }
            }

            else -> {
                if (isBg) {
                    bg?.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                } else {
                    setTextViewStyles(name, "#BBBBBB", "#BBBBBB")
                }
            }
        }

    }

    private fun setTextViewStyles(textView: TextView?, startColor: String?, endColor: String?) {
        val mLinearGradient = LinearGradient(0f, 0f, textView?.paint?.textSize!! * textView.text?.length!!, 0f, Color.parseColor(startColor), Color.parseColor(endColor), Shader.TileMode.CLAMP)
        textView.paint.shader = mLinearGradient
        textView.invalidate()
    }
}