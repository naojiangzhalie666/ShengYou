package com.xiaoshanghai.nancang.callback

import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

interface OnChatSeatClickCallback {
    /**
     * 点击座位弹窗回调
     * @param status 状态码 用于区分操作
     * @param userInfo  被点击人信息
     * @param userSeatInfo 被点击人座位信息
     */
    fun onSeatItemClick(status:Int, userInfo: VoiceRoomSeatEntity.UserInfo?)


}