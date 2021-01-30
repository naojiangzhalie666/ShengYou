package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.LotteryRecordContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.AwardsEntity
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class LotteryRecordModel: LotteryRecordContract.Model {
    override fun getLotterRecord(kind: Int): HttpObservable<BaseResponse<MutableList<AwardsEntity>>> {
        return HttpClient.getApi().getLotterRecord(kind)
    }

}