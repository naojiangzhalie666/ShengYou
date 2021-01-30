package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.MyCharmLvConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GradeResult

class MyCharmLvModel :MyCharmLvConstract.Model {
    override fun getCharmGrade(): HttpObservable<BaseResponse<GradeResult>> {
        return HttpClient.getApi().charmGrade
    }

}