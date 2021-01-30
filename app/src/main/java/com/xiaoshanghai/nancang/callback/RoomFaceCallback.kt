package com.xiaoshanghai.nancang.callback

import com.xiaoshanghai.nancang.net.bean.RoomFace

interface RoomFaceCallback {

    fun onDialogDismiss()

    fun onFaceClickResult(face:RoomFace?,isNobleFace:Boolean?,noble:String?)

}