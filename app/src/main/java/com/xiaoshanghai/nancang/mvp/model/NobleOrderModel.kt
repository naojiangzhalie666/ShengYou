package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.NobleOrderContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult

class NobleOrderModel : NobleOrderContract.Model {

    /**
     * 获取我的金币数量
     */
    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double>> = HttpClient.getApi().myGoldNum

    /**
     * 获取我的贵族身份
     */
    override fun getNoble(): HttpObservable<BaseResponse<Int>> = HttpClient.getApi().noble

    /**
     * 金币购买
     */
    override fun goldBuyNoble(nobleId: String): HttpObservable<BaseResponse<Int>> = HttpClient.getApi().goldBuyNoble(nobleId)

    /**
     * 获取令牌
     */
    override fun buyNoble(payType: String, payTargetId: String): HttpObservable<BaseResponse<BuyGoldResult>> = HttpClient.getApi().buyNoble(payType, payTargetId)
}