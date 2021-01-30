package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface RoomNotiyContract {

    interface View : BaseView {
        /**
         * 请求成功
         */
        fun onSuccess(status:Int?)

        /**
         * 请求接口失败
         */
        fun onError(msg:String?)

    }

    interface Presenter {

        /**
         * 请求设置房间公告接口
         */
        fun editRoomNotify(roomId: String?,noticeTitle: String?,noticeComment: String?)

    }

    interface Model {
        fun editRoomNotify(roomId: String?, noticeTitle: String?, noticeComment: String?): HttpObservable<BaseResponse<Int>>
    }

}