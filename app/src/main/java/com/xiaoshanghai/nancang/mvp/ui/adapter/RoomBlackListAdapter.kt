package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.RoomBlackListEntity
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class RoomBlackListAdapter:BaseQuickAdapter<RoomBlackListEntity, RoomBlackListAdapter.Holder>(R.layout.adapter_item_room_black_list) {



    override fun convert(holder: Holder, item: RoomBlackListEntity) {
        GlideAppUtil.loadImage(context, item.userPicture, holder.mIvAvatar, R.mipmap.icon_default_avatar)
        holder.mTvName?.text = item.userName
    }

    inner class Holder:BaseViewHolder {

        var mIvAvatar: CircleImageView? = null
        var mTvName: TextView? = null

        constructor(v: View) : super(v) {
            mIvAvatar = v.findViewById(R.id.civ_avatar)
            mTvName = v.findViewById(R.id.tv_name)
        }
    }
}