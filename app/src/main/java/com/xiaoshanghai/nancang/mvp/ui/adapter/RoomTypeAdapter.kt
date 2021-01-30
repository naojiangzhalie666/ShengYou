package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.mvp.ui.view.CustomTextView
import com.xiaoshanghai.nancang.net.bean.HomeSortResult

class RoomTypeAdapter(typeList: MutableList<HomeSortResult>?) : BaseQuickAdapter<HomeSortResult, RoomTypeAdapter.Holder>(layoutResId = R.layout.adapter_item_room_type, data = typeList) {

    override fun convert(holder: Holder, item: HomeSortResult) {
        holder.mTypeName?.text = item.roomTypeName
        if (item.isSelect) {
            holder.mTypeName?.setTextBgColor(item.roomTypeColor)
        } else {
            holder.mTypeName?.setTextBgColor("#565656")
        }
    }


    inner class Holder : BaseViewHolder {

        var mTypeName: CustomTextView? = null

        constructor(v: View) : super(v) {
            mTypeName = v.findViewById(R.id.tv_type_name)
        }
    }
}