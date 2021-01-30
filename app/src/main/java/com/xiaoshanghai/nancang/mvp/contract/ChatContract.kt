package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult

interface ChatContract {

    interface View : BaseView {

        fun onError(msg: String?)

        fun onGiftSuccess(roomGift: GiftAllResult?)

        fun onNobleSuccess(noble: Int?)
    }

    interface Presenter {

        fun getGift()

        fun getSelfNoble()

    }

    interface Model {
        /**
         * 获取房间礼物
         */
        fun getGift(): HttpObservable<BaseResponse<GiftAllResult>>

        fun getNoble(): HttpObservable<BaseResponse<Int>>

    }
}