package com.xiaoshanghai.nancang.callback

import com.xiaoshanghai.nancang.net.bean.RoomGiftResult

interface OnGiftCallback {

    fun setGiftResult(result: RoomGiftResult?, num: Int,fragmentIndex:Int)

    fun rechargeEntry()

}