package com.xiaoshanghai.nancang.callback

import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

interface OnLineClickCallback {
    /**
     * 点击座位弹窗回调
     * @param status 状态码 用于区分操作
     * @param userInfo  被点击人信息
     */
    fun onSeatItemClick(status:Int, userInfo: VoiceRoomSeatEntity.UserInfo?)


}