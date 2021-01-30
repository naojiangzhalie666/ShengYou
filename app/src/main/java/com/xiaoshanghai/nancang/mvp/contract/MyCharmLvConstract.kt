package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GradeResult

interface MyCharmLvConstract {

    interface View:BaseView{

        fun onCharmGradeSuccess(result: GradeResult?)

        fun onError(msg:String?)
    }

    interface Presenter {
        fun getCharmGradeUserGrade()
    }

    interface Model {
        fun getCharmGrade(): HttpObservable<BaseResponse<GradeResult>>
    }

}