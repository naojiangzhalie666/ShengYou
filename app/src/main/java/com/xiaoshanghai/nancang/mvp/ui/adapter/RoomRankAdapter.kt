package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.RoomRankResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class RoomRankAdapter : BaseQuickAdapter<RoomRankResult, RoomRankAdapter.Holder>(layoutResId = R.layout.adapter_room_fank) {


    override fun convert(holder: Holder, item: RoomRankResult) {
        val position = getItemPosition(item)
        holder.mTvRank?.text = (position + 4).toString()
        GlideAppUtil.loadImage(context, item.roomPicture, holder.mIvAvatar)
        holder.mTvName?.text = item.roomName
        holder.mTvId?.text = "ID:${item.roomNumber}"
        holder.mTvUpNum?.text = "${item.upPrice}"


    }


    inner class Holder : BaseViewHolder {
        var mTvRank: TextView? = null
        var mIvAvatar: CircleImageView? = null
        var mTvName: TextView? = null
        var mTvId: TextView? = null
        var mTvUpNum: TextView? = null

        constructor(v: View) : super(v) {
            mTvRank = v.findViewById(R.id.tv_rank)
            mIvAvatar = v.findViewById(R.id.iv_avatar)
            mTvName = v.findViewById(R.id.tv_name)
            mTvId = v.findViewById(R.id.tv_id)
            mTvUpNum = v.findViewById(R.id.tv_up_num)
        }
    }
}