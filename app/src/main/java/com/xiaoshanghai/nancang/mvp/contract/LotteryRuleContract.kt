package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.PrizeResult

interface LotteryRuleContract {

    interface View : BaseView {

        fun onPrizeSuccess(mPrize: PrizeResult?)

        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取奖池信息
         */
        fun getPrize(kind: Int)
    }

    interface Model {
        /**
         * 查看奖池
         */
        fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>>
    }
}