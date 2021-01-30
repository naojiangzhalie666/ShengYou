package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.NobleResult
import java.text.NumberFormat
import java.util.*

class NobleAdapter : BaseQuickAdapter<NobleResult, NobleAdapter.Holder>(layoutResId = R.layout.adapter_noble) {

    private var mType: Int = 0 //1.金币 2.支付宝 3.微信

    override fun convert(holder: Holder, item: NobleResult) {

        val noblePicture = getNoblePicture(item)

        if (mType == 1) {

            holder.mIvNobleAvatar.setImageResource(noblePicture.yellowPicture)
            holder.mTvNobleName.text = noblePicture.nobleName

            var price: String = if (noblePicture.ifBuy == 0) {

                NumberFormat.getNumberInstance(Locale.US).format(noblePicture.againCoinPrice)

            } else {

                NumberFormat.getNumberInstance(Locale.US).format(noblePicture.coinPrice)

            }
            holder.mTvNoble1.text = price
            holder.mTvNoble2.text = "金币"

            if (noblePicture.isSelect) {
                holder.mRlBg.setBackgroundResource(R.drawable.shape_noble_select)
                holder.mTvNobleName.setTextColor(ContextCompat.getColor(context, R.color.color_ff9b23))
                holder.mTvNoble1.setTextColor(ContextCompat.getColor(context, R.color.color_ff9b23))
                holder.mTvNoble2.setTextColor(ContextCompat.getColor(context, R.color.color_ff9b23))


            } else {
                holder.mRlBg.setBackgroundResource(R.drawable.shape_noble_un_select)
                holder.mTvNobleName.setTextColor(ContextCompat.getColor(context, R.color.color_caa351))
                holder.mTvNoble1.setTextColor(ContextCompat.getColor(context, R.color.color_black))
                holder.mTvNoble2.setTextColor(ContextCompat.getColor(context, R.color.color_black))
            }


        } else if (mType == 2 || mType == 3) {

            holder.mIvNobleAvatar.setImageResource(noblePicture.whitePicture)
            holder.mTvNobleName.text = noblePicture.nobleName

            var price: String? = if (noblePicture.ifBuy == 0) {
                NumberFormat.getNumberInstance(Locale.US).format(noblePicture.againRmbPrice)
            } else {
                NumberFormat.getNumberInstance(Locale.US).format(noblePicture.rmbPrice)
            }

            holder.mTvNoble1.text = "¥"
            holder.mTvNoble2.text = price

            if (noblePicture.isSelect) {
                holder.mRlBg.setBackgroundResource(R.drawable.shape_noble_select)
                holder.mTvNobleName.setTextColor(ContextCompat.getColor(context, R.color.color_ff9b23))
                holder.mTvNoble1.setTextColor(ContextCompat.getColor(context, R.color.color_ff9b23))
                holder.mTvNoble2.setTextColor(ContextCompat.getColor(context, R.color.color_ff9b23))


            } else {
                holder.mRlBg.setBackgroundResource(R.drawable.shape_noble_un_select)
                holder.mTvNobleName.setTextColor(ContextCompat.getColor(context, R.color.color_caa351))
                holder.mTvNoble1.setTextColor(ContextCompat.getColor(context, R.color.color_black))
                holder.mTvNoble2.setTextColor(ContextCompat.getColor(context, R.color.color_black))
            }


        }

    }

    inner class Holder(view: View) : BaseViewHolder(view) {

        var mRlBg: RelativeLayout = view.findViewById(R.id.rl_bg)
        var mIvNobleAvatar: ImageView = view.findViewById(R.id.iv_noble_avatar)
        var mTvNobleName: TextView = view.findViewById(R.id.tv_noble_name)
        var mTvNoble1: TextView = view.findViewById(R.id.tv_noble_1)
        var mTvNoble2: TextView = view.findViewById(R.id.tv_noble_2)

    }

    fun setType(type: Int) {
        this.mType = type
        notifyDataSetChanged()
    }

    private fun getNoblePicture(result: NobleResult): NobleResult {
        when (result.id) {
            "1" -> {
                result.whitePicture = R.mipmap.icon_w_baron
                result.yellowPicture = R.mipmap.icon_baron_y
            }

            "2" -> {
                result.whitePicture = R.mipmap.icon_w_viscount
                result.yellowPicture = R.mipmap.icon_viscount_y
            }

            "3" -> {
                result.whitePicture = R.mipmap.icon_w_earl
                result.yellowPicture = R.mipmap.icon_earl_y
            }

            "4" -> {
                result.whitePicture = R.mipmap.icon_w_marquis
                result.yellowPicture = R.mipmap.icon_marquis_y
            }

            "5" -> {
                result.whitePicture = R.mipmap.icon_w_duke
                result.yellowPicture = R.mipmap.icon_duke_y
            }

            "6" -> {
                result.whitePicture = R.mipmap.icon_w_king
                result.yellowPicture = R.mipmap.icon_king_y
            }

            "7" -> {
                result.whitePicture = R.mipmap.icon_w_emperor
                result.yellowPicture = R.mipmap.icon_emperor_y
            }

        }

        return result
    }

}