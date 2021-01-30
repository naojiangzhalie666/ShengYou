package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.AwardsEntity
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface LotteryRecordContract {

    interface View : BaseView {

        fun onRecordSuccess(record: MutableList<AwardsEntity>?)

        fun onError(msg: String?)

    }

    interface Presenter {

        fun getLotterRecord(kind: Int)
    }

    interface Model {

        fun getLotterRecord(kind: Int): HttpObservable<BaseResponse<MutableList<AwardsEntity>>>
    }

}