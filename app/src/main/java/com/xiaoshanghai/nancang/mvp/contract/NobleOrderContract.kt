package com.xiaoshanghai.nancang.mvp.contract

import android.os.Handler
import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult
import com.tencent.mm.opensdk.openapi.IWXAPI

interface NobleOrderContract {

    interface View : BaseView {

        /**
         * 请求金币数量成功
         */
        fun goldNumSuccess(gold: Double)

        /**
         * 获取贵族身份接口请求成功
         */
        fun nobleSuccess(noble: Int)

        /**
         * 金币购买接口成功
         */
        fun goldBuyNobleSuccess(status:Int)

        /**
         * 获取支付宝支付口令接口成功
         */
        fun aliBuySuccess(buyResult:BuyGoldResult)

        /**
         * 获取微信支付口令接口成功
         */
        fun wxBuySuccess(buyResult:BuyGoldResult)

        /**
         * 接口请求失败
         */
        fun onError(msg: String)
    }

    interface Presenter {
        /**
         * 获取我的金币数量
         */
        fun getMyGoldNum()

        /**
         * 获取我的贵族身份
         */
        fun getNoble()

        /**
         * 金币够购买
         */
        fun goldBuy(nobleId: String)

        /**
         * 支付宝购买
         */
        fun aliBuy(payTargetId: String)

        /**
         * 微信购买
         */
        fun wxBuy(payTargetId: String)

        /**
         * 支付宝支付
         * @param handler
         * @param orderString
         */
        fun aliPay(handler: Handler, orderString: String, what: Int)

        /**
         * 微信支付
         * @param result
         */
        fun weCharPay(api: IWXAPI, result: BuyGoldResult)

    }

    interface Model {
        /**
         * 获取我的金币
         */
        fun getMyGoldNum(): HttpObservable<BaseResponse<Double>>

        /**
         * 获取贵族身份
         */
        fun getNoble(): HttpObservable<BaseResponse<Int>>

        /**
         * 金币购买贵族
         */
        fun goldBuyNoble(nobleId: String): HttpObservable<BaseResponse<Int>>

        /**
         * RMB 购买贵族
         */
        fun buyNoble(payType: String, payTargetId: String): HttpObservable<BaseResponse<BuyGoldResult>>
    }

}