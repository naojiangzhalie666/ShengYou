package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.AwardsEntity
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class LotterRecordAdapter : BaseQuickAdapter<AwardsEntity, LotterRecordAdapter.Holder>(R.layout.adapter_item_lotter_record) {

    override fun convert(holder: Holder, item: AwardsEntity) {

        GlideAppUtil.loadImage(context, item.item?.gift?.giftStaticUrl, holder.mIvGift)
        holder.mTvGiftName?.text = item.item?.gift?.giftName
        holder.mTvPrice?.text = item.item?.gift?.giftPrice.toString()
        holder.mTvPriceType?.text = if (item.item?.gift?.giftPriceType == 1) "辣椒" else "金币"
        holder.mTvGiftNum?.text = "x${item.times}"
    }


    inner class Holder : BaseViewHolder {

        var mIvGift: CircleImageView? = null
        var mTvGiftName: TextView? = null
        var mTvPrice: TextView? = null
        var mTvPriceType: TextView? = null
        var mTvGiftNum: TextView? = null


        constructor(v: View) : super(v) {
            mIvGift = v.findViewById(R.id.iv_gift)
            mTvGiftName = v.findViewById(R.id.tv_gift_name)
            mTvPrice = v.findViewById(R.id.tv_price)
            mTvPriceType = v.findViewById(R.id.tv_price_type)
            mTvGiftNum = v.findViewById(R.id.tv_gift_num)
        }
    }


}