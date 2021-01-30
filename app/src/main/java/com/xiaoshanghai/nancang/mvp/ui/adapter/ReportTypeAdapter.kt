package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.ReportType

class ReportTypeAdapter : BaseQuickAdapter<ReportType, ReportTypeAdapter.Holder>(R.layout.adapter_report_type) {

    override fun convert(holder: Holder, item: ReportType) {
        holder.mTvContent?.text = item.reportKindComment
        if (item.isSelect!!) {
            holder.mIvFlag?.setImageResource(R.mipmap.icon_report_select_on)
        } else {
            holder.mIvFlag?.setImageResource(R.mipmap.icon_report_select_off)

        }
    }

    inner class Holder : BaseViewHolder {
        var mIvFlag: ImageView? = null
        var mTvContent: TextView? = null

        constructor(view: View) : super(view) {
            mIvFlag =  view.findViewById(R.id.iv_flag)
            mTvContent =  view.findViewById(R.id.tv_content)
        }
    }


}