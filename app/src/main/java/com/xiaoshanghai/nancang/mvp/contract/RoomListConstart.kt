package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.UserRankResult

interface RoomListConstart {

    interface View : BaseView {

        fun userRankSuccess(userRankList: MutableList<UserRankResult>?)

        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取房内榜 1贡献榜 日 2贡献榜 周 3魅力榜 日 4魅力榜 周
         */
        fun getUserRank(roomId: String?, type: Int?)
    }

    interface Model {

        fun getUserRank(roomId: String?, type: Int?): HttpObservable<BaseResponse<MutableList<UserRankResult>>>
    }

}