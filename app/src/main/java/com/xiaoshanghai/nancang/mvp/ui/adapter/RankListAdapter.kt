package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.UserRankResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.NumberUtils
import com.xiaoshanghai.nancang.view.CircleImageView

class RankListAdapter : BaseQuickAdapter<UserRankResult, RankListAdapter.Holder>(layoutResId = R.layout.adapter_rank_list) {


    override fun convert(holder: Holder, item: UserRankResult) {
        val position = getItemPosition(item)
        holder.mTvRank?.text = "${(position + 4)}"
        GlideAppUtil.loadImage(context, item.userPicture, holder.mIvAvatar)
        holder.mTvName?.text = item.userName
        holder.mIvSex?.setImageResource(if (item.userSex == 0) R.mipmap.icon_gender_female else R.mipmap.icon_gender_male)
        holder.mTvId?.text = "ID:${item.userNumber}"
        holder.mTvUpNum?.text = "${NumberUtils.strToNumber(item.countPrice.toString())}"
    }

    inner class Holder : BaseViewHolder {
        var mTvRank: TextView? = null
        var mIvAvatar: CircleImageView? = null
        var mTvName: TextView? = null
        var mIvSex: ImageView? = null
        var mTvId: TextView? = null
        var mTvUpNum: TextView? = null

        constructor(v: View) : super(v) {
            mTvRank = v.findViewById(R.id.tv_rank)
            mIvAvatar = v.findViewById(R.id.iv_avatar)
            mTvName = v.findViewById(R.id.tv_name)
            mIvSex = v.findViewById(R.id.iv_sex)
            mTvId = v.findViewById(R.id.tv_id)
            mTvUpNum = v.findViewById(R.id.tv_up_num)
        }
    }

}