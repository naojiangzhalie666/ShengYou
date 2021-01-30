package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.LotterSelectionConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.PrizeResult

class LotterSelectionModel : LotterSelectionConstract.Model {

    override fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>> {
        return HttpClient.getApi().getPrize(kind)
    }

}