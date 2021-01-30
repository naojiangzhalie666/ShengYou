package com.xiaoshanghai.nancang.net.bean

import android.graphics.Bitmap

data class RoomFace(
        var icon: Bitmap?,
        var filter: String?,
        var isGeneral: Boolean,
        var faceName:String?
//    val width: Int? = ScreenUtil.getPxByDp(32f),
//    var height: Int? = ScreenUtil.getPxByDp(32f)
)

