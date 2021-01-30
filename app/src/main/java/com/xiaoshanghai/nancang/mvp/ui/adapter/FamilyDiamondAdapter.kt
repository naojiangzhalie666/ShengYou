package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.mvp.ui.view.CharmLevelView
import com.xiaoshanghai.nancang.mvp.ui.view.UserLevelView
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class FamilyDiamondAdapter : BaseQuickAdapter<FamilyMemberResult, FamilyDiamondAdapter.Holder> {

    constructor() : super(R.layout.adapter_item_family_diamond) {
        addChildClickViewIds(R.id.tv_view)
    }

    override fun convert(holder: Holder, item: FamilyMemberResult) {
        GlideAppUtil.loadImage(context, item.userPicture, holder.mCivAvatar, R.mipmap.icon_default_avatar)
        if (item.isPatriarch == "1") {
            holder.mTvFlag?.visibility = View.VISIBLE
        } else {
            holder.mTvFlag?.visibility = View.GONE
        }

        holder.mNickName?.text = item.userName
        holder.mUlv?.setUserLevel(item.userLevel)
        holder.mClv?.setCharmLevel(item.charmLevel)
        if (item.coins > 0) {
            holder.mUserIncome?.text = "${String.format("%.1f", item.coins)}钻石"
        }

    }

    inner class Holder : BaseViewHolder {

        var mCivAvatar: CircleImageView? = null
        var mTvFlag: TextView? = null
        var mNickName: TextView? = null
        var mUlv: UserLevelView? = null
        var mClv: CharmLevelView? = null
        var mUserIncome: TextView? = null

        constructor(view: View) : super(view) {
            mCivAvatar = view.findViewById(R.id.civ_avatar)
            mTvFlag = view.findViewById(R.id.tv_patriarch_tag)
            mNickName = view.findViewById(R.id.tv_nick_name)
            mUlv = view.findViewById(R.id.ul_user_lv)
            mClv = view.findViewById(R.id.clv_charm_lv)
            mUserIncome = view.findViewById(R.id.tv_user_income)

        }
    }


}