package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.PrizePoolEntity
import com.xiaoshanghai.nancang.utils.GlideAppUtil

class PrizePoolAdapter : BaseQuickAdapter<PrizePoolEntity, PrizePoolAdapter.Holder>(R.layout.adapter_item_prize_pool) {

    private var type = 1

    override fun convert(holder: Holder, item: PrizePoolEntity) {
        val gift = item.gift
        GlideAppUtil.loadImage(context, gift?.giftStaticUrl, holder.mIvPrize)
        holder.mTvPrizeName?.text = gift?.giftName
        holder.mTvMoney?.text = gift?.giftPrice.toString()
       if(gift?.giftPriceType == 1) {
            holder.mTvMoneyType?.text = "辣椒"
       } else if (gift?.giftPriceType == 2) {
           holder.mTvMoneyType?.text = "金币"
       }

        if (type == 1) {
            holder.mTvPrizeName?.setTextColor(ContextCompat.getColor(context,R.color.color_e0e2f0))
            holder.mTvMoney?.setTextColor(ContextCompat.getColor(context,R.color.color_e0e2f0))
            holder.mTvMoneyType?.setTextColor(ContextCompat.getColor(context,R.color.color_e0e2f0))
        } else if (type == 2) {
            holder.mTvPrizeName?.setTextColor(ContextCompat.getColor(context,R.color.color_fbb332))
            holder.mTvMoney?.setTextColor(ContextCompat.getColor(context,R.color.color_fbb332))
            holder.mTvMoneyType?.setTextColor(ContextCompat.getColor(context,R.color.color_fbb332))
        }

    }

    fun setType(type:Int) {
        this.type = type
    }


    inner class Holder : BaseViewHolder {

        var mIvPrize: ImageView? = null
        var mTvPrizeName: TextView? = null
        var mTvMoney: TextView? = null
        var mTvMoneyType: TextView? = null

        constructor(view: View) : super(view) {
            mIvPrize = view.findViewById(R.id.iv_prize)
            mTvPrizeName = view.findViewById(R.id.tv_prize_name)
            mTvMoney = view.findViewById(R.id.tv_money)
            mTvMoneyType = view.findViewById(R.id.tv_money_type)

        }

    }


}