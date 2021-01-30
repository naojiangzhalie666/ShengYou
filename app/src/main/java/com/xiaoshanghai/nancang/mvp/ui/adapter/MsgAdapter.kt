package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.mvp.ui.view.*
import com.xiaoshanghai.nancang.net.bean.MsgBody


class MsgAdapter : BaseMultiItemQuickAdapter<MsgBody, MsgAdapter.Holder> {

    private var mContext: Context? = null
    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(context: Context?, list: MutableList<MsgBody>?, onItemClickListener: OnItemClickListener?) : super(data = list) {
        this.mContext = context
        this.mOnItemClickListener = onItemClickListener
        addItemType(MsgBody.MSG_IN_ROOM, R.layout.item_in_room)
        addItemType(MsgBody.MSG_TALK, R.layout.item_room_talk)
        addItemType(MsgBody.MSG_SEND_GIFT, R.layout.item_send_gift)
        addItemType(MsgBody.MSG_PUBLIC_SETTING, R.layout.item_public_settig)
        addItemType(MsgBody.MSG_PICE_SEAT, R.layout.item_public_settig)
        addItemType(MsgBody.MSG_LOTTERY, R.layout.item_lottery_barrage)

    }


    override fun convert(holder: Holder, item: MsgBody) {
        holder.bind(item, mOnItemClickListener, getItemPosition(item))
    }

    interface OnItemClickListener {
        fun onAgreeClick(position: Int, userId: String?)
    }

    inner class Holder(view: View) : BaseViewHolder(view) {
        fun bind(msgBody: MsgBody, listener: OnItemClickListener?, position: Int) {
            var userId: String? = null

            when (itemViewType) {

                MsgBody.MSG_IN_ROOM -> {
                    val mInRoom = getView<EnterRoomBarrage>(R.id.in_room_barrage)
                    mInRoom.barrageDisplay(msgBody)
                    userId = msgBody.userInfo.id

                }

                MsgBody.MSG_TALK -> {
                    val mInRoom = getView<RoomTalkBarrage>(R.id.room_talk)
                    mInRoom.barrageDisplay(msgBody)
                    userId = msgBody.userInfo.id

                }

                MsgBody.MSG_SEND_GIFT -> {
                    val mRoomSend = getView<RoomSendBarrage>(R.id.room_send)
                    mRoomSend.setMsgView(msgBody.sendGift)
                    userId = msgBody.sendGift.userId
                }

                MsgBody.MSG_PUBLIC_SETTING -> {
                    val mRoomPublic = getView<TextView>(R.id.tv_msg_2)
                    if (msgBody.type == MsgBody.MSG_PUBLIC_SETTING) {
                        if (msgBody.roomPublic) {
                            mRoomPublic.text = "管理员已开启聊天公屏"
                        } else {
                            mRoomPublic.text = "管理员已关闭聊天公屏"
                        }
                    }

                }

                MsgBody.MSG_PICE_SEAT -> {
                    val mRoomPublic = getView<TextView>(R.id.tv_msg_2)
                    if (msgBody.type == MsgBody.MSG_PICE_SEAT) {
                        if (msgBody.roomPick) {
                            mRoomPublic.text = "管理员已开启排麦模式"
                        } else {
                            mRoomPublic.text = "管理员已关闭排麦模式"
                        }
                    }
                }

                MsgBody.MSG_LOTTERY -> {
                    val mLotterBg = getView<LotteryBarrage>(R.id.lotter_bg)

                    mLotterBg.setLotterResult(msgBody.lottery)
                }

            }

            itemView.setOnClickListener { listener?.onAgreeClick(position, userId) }
        }

    }




}