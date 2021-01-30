package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.SystemNotic
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface SystemNoticContract {

    interface View:BaseView {

        /**
         * 刷新在线用户
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, onLineUser: MutableList<SystemNotic>?)

        /**
         * 加载更多在线用户
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, onLineUser: MutableList<SystemNotic>?)

        fun onError(msg: String?)
    }

    interface Presenter {

        fun getSystemNoic(refresh: RefreshLayout?)

    }

    interface Model {
        fun getSystemNotic(current: String?, size: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<SystemNotic>>>>
    }
}