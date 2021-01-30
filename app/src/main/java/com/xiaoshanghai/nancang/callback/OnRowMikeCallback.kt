package com.xiaoshanghai.nancang.callback

import com.xiaoshanghai.nancang.net.bean.RowSeatResult

interface OnRowMikeCallback {
    fun onRowMikeCallback(rowSeatResult: RowSeatResult?)
}