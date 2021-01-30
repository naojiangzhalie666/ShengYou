package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.xiaoshanghai.nancang.R

class ReportHeaderView : RelativeLayout {

    private var mView: View? = null

    private var mContext:Context? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context?) {
        mContext = context
        mView = LayoutInflater.from(context).inflate(R.layout.view_report_header, this, true)
    }
}