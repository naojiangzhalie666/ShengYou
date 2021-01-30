package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.ReportContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.ReportType
import okhttp3.RequestBody

class ReportModel:ReportContract.Model {

    override fun getReportType(): HttpObservable<BaseResponse<MutableList<ReportType>>> {
        return HttpClient.getApi().reportType
    }

    override fun report(files: Map<String, RequestBody>?, userId: String?, reportType: String?, reportKindId: String?, targetId: String?, reportComment: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().report(files, userId, reportType, reportKindId, targetId, reportComment)
    }

}