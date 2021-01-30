package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity

class RoomBgNobleView : ConstraintLayout {

    @BindView(R.id.iv_noble_bg)
    lateinit var mIvNobleBg: ImageView

    @BindView(R.id.tv_noble_name)
    lateinit var mTvNobleName: TextView

    lateinit var mContext: Context

    lateinit var mView: View

    constructor(context: Context) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_room_bg_noble, this, true)
        ButterKnife.bind(mView)
    }

    fun setResult(roomBg: RoomBgEntity){
        when(roomBg.nobleId){
            2 -> {
                mIvNobleBg.setImageResource(R.mipmap.icon_room_noble_2)
                mTvNobleName.text = "子爵"
            }

            3 -> {
                mIvNobleBg.setImageResource(R.mipmap.icon_room_noble_3)
                mTvNobleName.text = "伯爵"
            }

            4 -> {
                mIvNobleBg.setImageResource(R.mipmap.icon_room_noble_4)
                mTvNobleName.text = "侯爵"
            }

            5 -> {
                mIvNobleBg.setImageResource(R.mipmap.icon_room_noble_5)
                mTvNobleName.text = "公爵"
            }

            6 -> {
                mIvNobleBg.setImageResource(R.mipmap.icon_room_noble_6)
                mTvNobleName.text = "国王"
            }

            7 -> {
                mIvNobleBg.setImageResource(R.mipmap.icon_room_noble_7)
                mTvNobleName.text = "皇帝"
            }

            else -> {
                mIvNobleBg.setImageResource(0)
                mTvNobleName.text = ""
            }

        }
    }


}