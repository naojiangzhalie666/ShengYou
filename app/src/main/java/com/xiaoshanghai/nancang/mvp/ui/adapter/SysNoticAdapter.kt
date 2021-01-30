package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.SystemNotic

class SysNoticAdapter : BaseQuickAdapter<SystemNotic, SysNoticAdapter.Holder>(R.layout.adapter_item_sys_notic) {

    override fun convert(holder: Holder, item: SystemNotic) {
        holder.mTitle?.text = item.notifyTitle
        holder.mTvContent?.text = item.notifyContent
    }

    inner class Holder : BaseViewHolder {

        var mTitle: TextView? = null
        var mTvContent: TextView? = null

        constructor(view: View) : super(view) {
            mTitle = view.findViewById(R.id.tv_title)
            mTvContent = view.findViewById(R.id.tv_content)
        }
    }

}