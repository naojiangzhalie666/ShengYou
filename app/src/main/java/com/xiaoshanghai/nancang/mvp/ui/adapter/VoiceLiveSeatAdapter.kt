package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.mvp.ui.view.MikeSeatView
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

class VoiceLiveSeatAdapter : BaseQuickAdapter<VoiceRoomSeatEntity, VoiceLiveSeatAdapter.Holder> {

    private var mContext: Context? = null

    private var list: List<VoiceRoomSeatEntity>? = null

    private var onItemClickListener: OnItemClickListener? = null

    private var mGiftValue = false

    constructor(context: Context, list: MutableList<VoiceRoomSeatEntity>?, giftValue: Boolean, onItemClickListener: OnItemClickListener) :
            super(layoutResId = R.layout.adapter_item_seat_layout, data = list) {

        this.mContext = context
        this.list = list
        this.onItemClickListener = onItemClickListener
        this.mGiftValue = giftValue

    }

    override fun convert(holder: Holder, item: VoiceRoomSeatEntity) {
        val itemPosition = getItemPosition(item)
        holder.bind(item, onItemClickListener,mGiftValue, itemPosition)
    }

    fun setGiftValue(giftValue:Boolean) {
        this.mGiftValue = giftValue
        for (datum in data) {
            datum.userInfo?.giftCoinCount = 0
        }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class Holder : BaseViewHolder {

        var mMikeSeat: MikeSeatView? = null

        constructor(view: View) : super(view) {

            mMikeSeat = view.findViewById(R.id.mike_seat)

        }

        fun bind(model: VoiceRoomSeatEntity?, listener: OnItemClickListener?, giftValue:Boolean,position: Int) {
            itemView.setOnClickListener { listener?.onItemClick(layoutPosition) }
            mMikeSeat!!.setSeatResult(model,giftValue, position)

        }

    }
}