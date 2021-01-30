package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.MyUserLvConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GradeResult

class MyUserLvModel :MyUserLvConstract.Model {
    override fun getUserGrade(): HttpObservable<BaseResponse<GradeResult>> {
        return HttpClient.getApi().userGrade
    }
}