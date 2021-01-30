package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface OnLineContract {

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

    }

    interface Presenter {

        /**
         * 加载在线用户
         * @param refresh
         */
        fun loadOnLineUser(refresh: RefreshLayout?, roomId: String?)

    }

    interface Model {
        /**
         * 加载在线用户
         */
        fun getOnLineUser(current: String?, size: String?, roomId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>>
    }

}