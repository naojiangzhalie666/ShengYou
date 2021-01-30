package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.ChatGiftSeat
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class ChatGiftSeatAdapter : BaseQuickAdapter<ChatGiftSeat, ChatGiftSeatAdapter.Holder> {

    constructor(seatList: MutableList<ChatGiftSeat>) : super(R.layout.adapter_room_gift_seat, seatList) {
        addChildClickViewIds()
    }


    override fun convert(holder: Holder, item: ChatGiftSeat) {


        GlideAppUtil.loadImage(context, item.userPicture, holder.civ)


        holder.mTvIndex?.visibility = View.GONE
        holder.mIvRoomFlag?.visibility = View.GONE

        if (item.isSelect!!) {
            holder.civ?.setBorderWidth(2.0f)
            holder.civ?.setBorderColor(Color.parseColor("#FF5F85"))
        } else {
            holder.civ?.setBorderWidth(0f)
        }


    }

    inner class Holder : BaseViewHolder {

        var civ: CircleImageView? = null
        var mTvIndex: TextView? = null
        var mIvRoomFlag: ImageView? = null

        constructor(mView: View) : super(mView) {
            civ = mView.findViewById(R.id.iv_avatar)
            mTvIndex = mView.findViewById(R.id.tv_index)
            mIvRoomFlag = mView.findViewById(R.id.iv_room_flag)
        }
    }


}