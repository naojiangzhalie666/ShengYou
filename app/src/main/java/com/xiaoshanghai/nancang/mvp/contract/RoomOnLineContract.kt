package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.callback.FollowCallback
import com.xiaoshanghai.nancang.callback.RoomUserCallback
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

interface RoomOnLineContract {

    interface View : BaseView {

        fun onError(msg:String?)

        /**
         * 刷新在线用户
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, onLineUser: MutableList<OnLineUserResult>)

        /**
         * 加载更多在线用户
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, onLineUser: MutableList<OnLineUserResult>)

        /**
         * 关注
         */
        fun onFollow(status: String?)

        /**
         * 取消关注
         */
        fun onUnFollow(Status: String?)

        /**
         * 添加管理员
         */
        fun onAddManager(status: Int?, userId: String?)

        /**
         * 移除管理员
         */
        fun onRemoveManager(status: Int?, userId: String?)

        /**
         * 添加或移除黑名单
         */
        fun onAddAndRemoveBlackSuccess(status: String?, resultStatus: Int?, roomId: String?, userId: String?)

    }

    interface Presenter {

        /**
         * 加载在线用户
         * @param refresh
         */
        fun loadOnLineUser(refresh: RefreshLayout?, roomId: String?)

        /**
         * 请求用户资料
         */
        fun getUser(userId: String?, roomId: String?, mRoomUser: RoomUserCallback?)

        /**
         * 查询关注状况
         */
        fun queryFollow(userId: String, callback: FollowCallback?)

        /**
         * 关注或是取消关注
         */
        fun follow(userId: String?, status: String?)

        /**
         * 添加管理员
         */
        fun addManager(roomId: String?, userId: String?)

        /**
         * 移除管理员
         */
        fun removeManager(roomId: String?, userId: String?)

        /**
         * 添加或者移除黑名单
         */
        fun addBlackList(status: String?, roomId: String?, userId: String?)

        /**
         * 移除黑名单
         */
        fun removeBlackList(status: String?, roomId: String?, userId: String?)


    }

    interface Model {
        /**
         * 加载在线用户
         */
        fun getOnLineUser(current: String?, size: String?, roomId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>>

        /**
         * 获取用户信息
         */
        fun getRoomUser(userId: String?, roomId: String): HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>>

        /**
         * 查询关注
         */
        fun queryFollow(userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 关注或是取消关注
         */
        fun follow(userId: String?, status: String?): HttpObservable<BaseResponse<String>>

        /**
         * 添加管理员
         */
        fun addManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 移除管理员
         */
        fun removeManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 添加和移除黑名单
         */
        fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 移除黑名单
         */
        fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

    }

}