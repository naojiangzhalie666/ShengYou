package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.LotteryContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.LotteryPirzeResult
import com.xiaoshanghai.nancang.net.bean.PrizeResult

class LotteryModel : LotteryContract.Model {

    override fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>> {
        return HttpClient.getApi().getPrize(kind)
    }

    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double>> {
        return HttpClient.getApi().myGoldNum
    }

    override fun lottery(type: Int, kind: Int): HttpObservable<BaseResponse<LotteryPirzeResult>> {
        return HttpClient.getApi().lottery(type,kind)
    }

}