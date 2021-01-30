package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HalfRoomRankResult

interface HalfRankConstract {

    interface View : BaseView {

        /**
         * 成功获取排行榜
         */
        fun rankSuccess(rank: HalfRoomRankResult?)

        fun onError(msg: String?)

    }

    interface Presenter {

        /**
         * 获取房间排行榜
         */
        fun getRoomRank(roomId: String?)

    }

    interface Model {

        fun getHalfRoomRank(roomId: String?): HttpObservable<BaseResponse<HalfRoomRankResult>>

    }
}