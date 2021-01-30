package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.RowSeatResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class RowSeatViewAdapter : BaseQuickAdapter<RowSeatResult, RowSeatViewAdapter.Holder> {

    private var mKind: Int = -1

    constructor(kind: Int) : super(R.layout.adapter_view_seat) {
        this.mKind = kind
        addChildClickViewIds(R.id.tv_row_seat)
    }

    override fun convert(holder: Holder, item: RowSeatResult) {
        val position = getItemPosition(item)
        holder.mTvSeiralNum?.text = (position + 1).toString()
        GlideAppUtil.loadImage(context, item.userPictrue, holder.mCivAvatar, R.mipmap.icon_default_avatar)
        holder.mTvName?.text = item.userName
        holder.mIvSex?.setImageResource(if (item.sax!! == 0) R.mipmap.icon_gender_female else R.mipmap.icon_gender_male)
        if (mKind == 1||mKind == 2) {
            holder.mTvRowSeat?.text = "抱TA上麦"
            holder.mTvRowSeat?.setBackgroundResource(R.drawable.shape_p_r12_bg)
            addChildClickViewIds(R.id.tv_row_seat)
        } else if (mKind == 3){
            holder.mTvRowSeat?.text = "排麦中"
            holder.mTvRowSeat?.setBackgroundResource(R.drawable.shape_g_r12_bg)
        }
    }


    inner class Holder : BaseViewHolder {

        var mTvSeiralNum: TextView? = null

        var mCivAvatar: CircleImageView? = null

        var mTvName: TextView? = null

        var mIvSex: ImageView? = null

        var mTvRowSeat: TextView? = null

        constructor(view: View) : super(view) {
            mTvSeiralNum = view.findViewById(R.id.tv_seiral_num)
            mCivAvatar = view.findViewById(R.id.civ_avatar)
            mTvName = view.findViewById(R.id.tv_name)
            mIvSex = view.findViewById(R.id.iv_sex)
            mTvRowSeat = view.findViewById(R.id.tv_row_seat)
        }

    }

}