package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface FeedBackContract {
    interface View : BaseView {
        fun feedBackSuccess()
        fun feedBackError(msg: String)
    }

    interface Presenter {
        fun  feedBack(message:String,type:String,num:String,userId:String)

    }

    interface Model {
        fun  feedBack(message:String,type:String,num:String,userId:String): HttpObservable<BaseResponse<Int>>?
    }
}