package com.xiaoshanghai.nancang.callback

interface RoomInPutCallback {
    fun onInPutResult(msg: String?)

    fun onInputDismiss()
}