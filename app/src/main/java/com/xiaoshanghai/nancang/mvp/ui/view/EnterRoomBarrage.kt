package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.MsgBody

class EnterRoomBarrage : LinearLayout {

    @BindView(R.id.ll_msg_bg)
    lateinit var mMsgBg: LinearLayout

    @BindView(R.id.iv_noble)
    lateinit var mIvNoble: ImageView

    @BindView(R.id.iv_clv)
    lateinit var mIvClv: CharmLevelView

    @BindView(R.id.tv_name)
    lateinit var mTvName: TextView

    lateinit var mView: View

    lateinit var mContext: Context

    constructor(context: Context) : this(context,null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,attrs ,defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_enter_room_barrage, this, true)
        ButterKnife.bind(mView)
    }

    fun barrageDisplay(msgBody: MsgBody?) {

        if (msgBody?.userInfo?.charmLevel!! >= 70) {
            mIvClv.visibility = View.VISIBLE
        } else {
            mIvClv.visibility = View.GONE
        }

        mIvClv.setCharmLevel(msgBody?.userInfo?.charmLevel!!)

        mTvName.text = msgBody.userInfo?.userName

        when (msgBody.userInfo?.noble) {

            "0" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                mIvNoble.visibility = View.GONE
                setTextViewStyles(mTvName, "#BBBBBB", "#BBBBBB")
            }

            "1" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_1)
                mIvNoble.setImageResource(R.mipmap.img_baron)
                setTextViewStyles(mTvName, "#C6F1F1", "#C6F1F1")
            }

            "2" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_2)
                mIvNoble.setImageResource(R.mipmap.img_viscount)
                setTextViewStyles(mTvName, "#FFD3A7", "#FFD3A7")
            }

            "3" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_0)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_3)
                mIvNoble.setImageResource(R.mipmap.img_earl)
                setTextViewStyles(mTvName, "#ED6C44", "#FFB230")
            }

            "4" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_4)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_4)
                mIvNoble.setImageResource(R.mipmap.img_marquis)
                setTextViewStyles(mTvName, "#06B4FD", "#6CF3FF")
            }

            "5" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_5)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_5)
                mIvNoble.setImageResource(R.mipmap.img_duke)
                setTextViewStyles(mTvName, "#FF57CE", "#B735F3")
            }

            "6" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_6)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_6)
                mIvNoble.setImageResource(R.mipmap.img_king)
                setTextViewStyles(mTvName, "#FFFF00", "#FF6E02")
            }

            "7" -> {
                mMsgBg.setBackgroundResource(R.drawable.shape_in_room_noble_7)
                mIvNoble.visibility = View.VISIBLE
//                mIvNoble.setImageResource(R.mipmap.icon_noble_7)
                mIvNoble.setImageResource(R.mipmap.img_emperor)
                setTextViewStyles(mTvName, "#F00000", "#FC726D")
            }
        }

    }


    private fun setTextViewStyles(textView: TextView, startColor: String, endColor: String) {
        val mLinearGradient = LinearGradient(0f, 0f, textView.paint.textSize * textView.text.length, 0f, Color.parseColor(startColor), Color.parseColor(endColor), Shader.TileMode.CLAMP)
        textView.paint.shader = mLinearGradient
        textView.invalidate()
    }

}