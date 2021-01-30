package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.mvp.ui.view.AvatarView
import com.xiaoshanghai.nancang.mvp.ui.view.CharmLevelView
import com.xiaoshanghai.nancang.mvp.ui.view.UserLevelView
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.xiaoshanghai.nancang.utils.UserManagerUtils

class OnLineAdapter : BaseQuickAdapter<OnLineUserResult, OnLineAdapter.Holder>(R.layout.adapter_item_online) {

    override fun convert(holder: Holder, item: OnLineUserResult) {

        if (item.noble!!.toInt() >= 7) {
            holder.mAvatar?.setDefAvatarAndHeadear(R.mipmap.icon_man, R.mipmap.icon_mystery)
            holder.mTvName?.text = "神秘人"
        } else {
            holder.mAvatar?.setAvatarAndHeadear(item.userPicture, null)
            holder.mTvName?.text = item.userName
        }

        if (item.userSex == 0) {
            holder.mIvSex?.setImageResource(R.mipmap.icon_gender_female)
        } else {
            holder.mIvSex?.setImageResource(R.mipmap.icon_gender_male)
        }

        val nobleImage = UserManagerUtils.nobleImage(item.noble!!.toInt())
        if (nobleImage == 0) {
            holder.mIvNoble?.visibility = View.GONE
        } else {
            holder.mIvNoble?.setImageResource(nobleImage)
        }

        holder.ulv?.setUserLevel(item.userLevel!!)

        holder.clv?.setCharmLevel(item.charmLevel!!)
        setKinds(holder, item.userKind!!, item.onMike!!)

    }

    private fun setKinds(holder: Holder, kind: Int, onMike: Int) {
        if (onMike == 0) {
            holder.mTvUpSeat?.visibility = View.GONE
        } else if (onMike == 1) {
            holder.mTvUpSeat?.visibility = View.VISIBLE
        }

        when (kind) {
            1 -> {
                holder.mTvHomeowner?.visibility = View.VISIBLE
                holder.mTvManager?.visibility = View.GONE
            }
            2 -> {
                holder.mTvHomeowner?.visibility = View.GONE
                holder.mTvManager?.visibility = View.VISIBLE
            }
            3 -> {
                holder.mTvHomeowner?.visibility = View.GONE
                holder.mTvManager?.visibility = View.GONE
            }
        }


    }


    inner class Holder : BaseViewHolder {

        var mAvatar: AvatarView? = null

        var mTvName: TextView? = null

        var mIvSex: ImageView? = null

        var mIvNoble: ImageView? = null

        var ulv: UserLevelView? = null

        var clv: CharmLevelView? = null

        var mTvUpSeat: TextView? = null

        var mTvManager: TextView? = null

        var mTvHomeowner: TextView? = null

        constructor(view: View) : super(view) {
            mAvatar = view.findViewById(R.id.tv_avatar)
            mTvName = view.findViewById(R.id.tv_name)
            mIvSex = view.findViewById(R.id.iv_sex)
            mIvNoble = view.findViewById(R.id.iv_noble)
            ulv = view.findViewById(R.id.ulv)
            clv = view.findViewById(R.id.clv)
            mTvUpSeat = view.findViewById(R.id.tv_up_seat)
            mTvManager = view.findViewById(R.id.tv_room_manager)
            mTvHomeowner = view.findViewById(R.id.tv_homeowner)
        }
    }


}