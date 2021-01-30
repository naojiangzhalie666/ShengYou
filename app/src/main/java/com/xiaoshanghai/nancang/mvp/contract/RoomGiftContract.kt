package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface RoomGiftContract {

    interface View : BaseView {

        fun onGoldSuccess(gold: Double?)

        fun onChiliSuccess(chili: Double?)

        fun onError(msg: String?)

    }

    interface Presetner {
        /**
         * 获取金币数量
         */
        fun getMyGoldNum()

        /**
         * 获取我的辣椒总量
         */
        fun getMyChili()
    }

    interface Model {

        /**
         * 获取金币总量
         */
        fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>?

        /**
         * 获取辣椒
         * @return
         */
        fun getMyChili(): HttpObservable<BaseResponse<Double?>?>?
    }

}