package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface RoomBackgroundConstant {

    interface View : BaseView {

        /**
         * 请求黑名单列表成功
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, roomBg: MutableList<RoomBgEntity>)

        /**
         * 请求黑名单列表加载成功
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, roomBg: MutableList<RoomBgEntity>)


        fun onError(msg: String?)


    }

    interface Presenter {

        fun getRoomBg(refreshLayout: RefreshLayout?)

    }

    interface Model {

        fun getRoomBg(current:String?,size:String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBgEntity>>>>

    }

}