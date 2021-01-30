package com.xiaoshanghai.nancang.callback

interface RoomSettingCallback {

    fun onTxSwitch(isOpenTx: Boolean?)

    fun onGpSwitch(isOpenGp: Boolean?)

    fun onLwzSwitch(isOpenLwz: Boolean?)

    fun onSetting()

    fun onSettingDismiss()
}