package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity

interface RoomBgConstract {

    interface View : BaseView {

        fun onSuccess(status:Int?,roomBg: RoomBgEntity?)

        fun onNobleSuccess(noble:Int?)

        fun onError(msg:String?)

    }

    interface Presenter {

        fun setRoomBg(roomId: String?, userId: String?, roomBg: RoomBgEntity?)

        fun getMyNoble()

    }

    interface Model {

        fun setRoomBg(roomId: String?, userId: String?,imgId: String?): HttpObservable<BaseResponse<Int>>

        fun getNoble(): HttpObservable<BaseResponse<Int>>

    }
}