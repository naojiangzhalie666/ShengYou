package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface NobleSettingContract {

    interface View:BaseView {

        fun onStatusSuccess(status:String?,result:String?)

        fun onError(msg:String?)


    }

    interface Presenter {

        fun setRoomStatus(userId: String?,status: String?)

    }

    interface Model {

        fun enterRoomStatus(userId: String?,status: String?): HttpObservable<BaseResponse<String>>

    }
}