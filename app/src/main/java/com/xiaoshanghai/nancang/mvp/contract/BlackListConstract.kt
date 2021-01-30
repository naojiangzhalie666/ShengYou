package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.RoomBlackListEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface BlackListConstract {

    interface View : BaseView {

        /**
         * 请求黑名单列表成功
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, roomBlack: MutableList<RoomBlackListEntity>)

        /**
         * 请求黑名单列表加载成功
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, roomBlack: MutableList<RoomBlackListEntity>)


        fun removeSuccess(status:Int,blackUser: RoomBlackListEntity)


        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取黑名单列表
         *
         */
        fun getRoomBlackList(refreshLayout: RefreshLayout?, currentId: String?)

        fun removeBlack(mRoomId: String, blackEntity: RoomBlackListEntity)


    }

    interface Model {

        fun getRoomBlackList(current: String?, size: String?, blacklistType: String?, current_id: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBlackListEntity>>>>

        /**
         * 移除黑名单
         */
        fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

    }
}