package com.xiaoshanghai.nancang.mvp.ui.adapter

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.FamilyIncome

class FamilyIncomeAdapter : BaseSectionQuickAdapter<FamilyIncome, BaseViewHolder> {


    constructor(data: MutableList<FamilyIncome>?) : super(R.layout.adapter_family_income_headre, data) {
        setNormalLayout(R.layout.adapter_family_income)
    }

    override fun convertHeader(holder: BaseViewHolder, item: FamilyIncome) {
        holder.setText(R.id.tv_date, item.date)
    }

    override fun convert(holder: BaseViewHolder, item: FamilyIncome) {
        holder.setText(R.id.tv_time, item.userIncome?.time)
        holder.setText(R.id.tv_money, "+${(item.userIncome?.number)?.toInt()}")
    }


}