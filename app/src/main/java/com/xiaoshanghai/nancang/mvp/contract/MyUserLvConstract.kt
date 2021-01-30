package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GradeResult

interface MyUserLvConstract {

    interface View:BaseView{

        fun onUserGradeSuccess(result:GradeResult?)

        fun onError(msg:String?)
    }

    interface Presenter {
        fun getUserGrade()
    }

    interface Model {
        fun getUserGrade(): HttpObservable<BaseResponse<GradeResult>>
    }

}