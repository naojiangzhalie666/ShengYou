package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface RoomSettingContract {

    interface View : BaseView {

        fun roomNameSuccess(status: Int?, roomName: String?)

        fun roomPswSuccess(status: Int?, isLock: Boolean, psw: String?)

        fun isOpenGpSuccess(status: Int?, isOpen: Boolean?)

        fun onPickSwitch(status: Int?, isPick: Boolean?)

        fun onError(msg: String?)

    }

    interface Presenter {

        fun modifyRoomName(roomId: String?, roomName: String?)

        fun modifyRoomPsw(roomId: String, type: String, password: String?)

        /**
         * 设置公屏
         */
        /**
         * 修改房间公屏 1公屏 0关闭公屏
         */
        fun setGpSwitch(roomId: String, type: Boolean)

        /**
         * 抱麦开关
         */
        fun pickSwitch(roomId: String?, type: Int?, isPick: Boolean?)

    }

    interface Model {

        /**
         * 修改房间名
         */
        fun modifyName(roomId: String?, roomName: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 修改密码
         */
        fun modifyPsw(roomId: String?, type: String?, password: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 公屏接口
         */
        fun setPublic(roomId: String?, type: Int): HttpObservable<BaseResponse<Int>>

        /**
         * 抱麦开关
         */
        fun pickSwitch(roomId: String?, type: String?): HttpObservable<BaseResponse<Int>>

    }

}
