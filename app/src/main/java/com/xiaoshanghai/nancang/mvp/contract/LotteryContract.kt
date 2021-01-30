package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.LotteryPirzeResult
import com.xiaoshanghai.nancang.net.bean.PrizeResult

interface LotteryContract {

    interface View : BaseView {


        fun onPrizeSuccess(mPrize: PrizeResult?)

        fun onGoldSuccess(balance: Double)

        fun onLotterySuccess(result: LotteryPirzeResult?)

        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取奖池信息
         */
        fun getPrize(kind: Int)

        /**
         * 获取我的金币余额
         */
        fun getMyGold()

        /**
         * 抽奖
         */
        fun lottery(type: Int, kind: Int)
    }

    interface Model {
        /**
         * 查看奖池
         */
        fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>>

        /**
         * 获取我的金币余额
         */
        fun getMyGoldNum(): HttpObservable<BaseResponse<Double>>

        /**
         * 抽奖
         */
        fun lottery(type: Int, kind: Int): HttpObservable<BaseResponse<LotteryPirzeResult>>
    }


}