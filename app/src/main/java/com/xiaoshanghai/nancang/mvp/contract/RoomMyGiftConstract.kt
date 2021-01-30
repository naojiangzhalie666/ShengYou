package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult

interface RoomMyGiftConstract {

    interface View : BaseView {

        fun onGiftSuccess(giftList: MutableList<RoomGiftResult>)

        fun onGoldSuccess(gold: Double?)

        fun onChiliSuccess(chili: Double?)

        fun onError(msg: String?)

        fun onTotalSuccess(status:Int?)

        fun onTotalError(msg:String?)

    }

    interface Presenter {

        fun getPackageGift()

        /**
         * 获取金币数量
         */
        fun getMyGoldNum()

        /**
         * 获取我的辣椒总量
         */
        fun getMyChili()

        /**
         * 获取礼物总价值
         */
        fun getTotalValue();

    }

    interface Model {

        fun getPackageGift(): HttpObservable<BaseResponse<MutableList<RoomGiftResult>>>

        /**
         * 获取金币总量
         */
        fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>?

        /**
         * 获取辣椒
         * @return
         */
        fun getMyChili(): HttpObservable<BaseResponse<Double?>?>?

        /**
         * 获取背包总价值
         */
        fun getTotalValue(): HttpObservable<BaseResponse<Int?>?>?



    }
}