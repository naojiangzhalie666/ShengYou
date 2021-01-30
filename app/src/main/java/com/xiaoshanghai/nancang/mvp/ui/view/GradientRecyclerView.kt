package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.tencent.qcloud.tim.uikit.utils.ScreenUtil

class GradientRecyclerView : RecyclerView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        //列表是否垂直
        isVerticalFadingEdgeEnabled = true
        //阴影高度
        setFadingEdgeLength(ScreenUtil.getPxByDp(100.0f))
    }   /**
     * 顶部阴影强度，这里用系统的默认效果，所以没有重写
     */
    override fun getTopFadingEdgeStrength(): Float {
        return super.getTopFadingEdgeStrength()
    }

    /**
     * 底部阴影强度，这里不需要，所以设置为0f
     */
    override fun getBottomFadingEdgeStrength(): Float {
        return 0f
    }
}