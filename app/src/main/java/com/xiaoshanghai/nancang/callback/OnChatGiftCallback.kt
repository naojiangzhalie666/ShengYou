package com.xiaoshanghai.nancang.callback

import com.xiaoshanghai.nancang.net.bean.ChatGiftSeat
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult

interface OnChatGiftCallback {

    fun onSendGiftResult(status: Int?, users: MutableList<String>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?,mGiveAway: ChatGiftSeat?)

    fun onNobleClick()

    fun onGiftDismiss()
}