package com.xiaoshanghai.nancang.callback

import com.xiaoshanghai.nancang.net.bean.GiftViewSeat
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult

interface OnGiftSendCallback {

    fun onSendGiftResult(status: Int?, users: MutableList<GiftViewSeat>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?)

    fun onNobleClick()

    fun onGiftDismiss()
}