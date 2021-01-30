package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.ReportType
import okhttp3.RequestBody

interface ReportContract {

    interface View : BaseView {

        fun onReproTypeSuccess(reportList: MutableList<ReportType>?)

        fun onReportSuccess(status:String?)

        fun onError(msg: String?)

    }

    interface Presenter {

        fun getReportType()

        /**
         *
         * 举报
         * @param photoPath 	图片地址数组
         * @param userId 	用户id
         * @param reportType 举报类型举报类型1举报房间 2举报个人
         * @param reportKindId 举报种类id关联report_kind表id
         * @param targetId 举报对象id如果report_type=1，这里填入的是房间id，如果report_type=2，这里填入的是用户id
         * @param reportComment 举报类容
         *
         */
        fun report(photoPath: MutableList<String>?,
                   userId: String?,
                   reportType: String?,
                   reportKindId: String?,
                   targetId: String?,
                   reportComment: String?)

    }

    interface Model {
        /**
         * 获取举报类型
         */
        fun getReportType(): HttpObservable<BaseResponse<MutableList<ReportType>>>

        /**
         *
         * 举报
         * @param files 	图片地址数组
         * @param userId 	用户id
         * @param reportType 举报类型举报类型1举报房间 2举报个人
         * @param reportKindId 举报种类id关联report_kind表id
         * @param targetId 举报对象id如果report_type=1，这里填入的是房间id，如果report_type=2，这里填入的是用户id
         * @param reportComment 举报类容
         *
         */
        fun report(files: Map<String, RequestBody>?,
                   userId: String?,
                   reportType: String?,
                   reportKindId: String?,
                   targetId: String?,
                   reportComment: String?): HttpObservable<BaseResponse<String>>
    }

}