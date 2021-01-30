package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface GoldGiftOutlayContract {

    interface View : BaseView {

        fun onError(msg: String?)

        /**
         * 设置时间
         *
         * @param date
         */
        fun setTime(date: String?)

        /**
         * 刷新成功
         *
         * @param refresh
         * @param incomeList
         */
        fun refreshSuccess(refresh: RefreshLayout?, incomeList: List<GoldGiftResult?>?)

        /**
         * 加载成功
         *
         * @param refresh
         * @param incomeList
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, incomeList: List<GoldGiftResult?>?)
    }

    interface Presenter {
        /**
         * 时间选择
         */
        fun selectTime(date: String?)

        /**
         * 获取收入列表
         *
         * @param date
         */
        fun getGoldGiftOutlayList(refresh: RefreshLayout?, date: String?)
    }

    interface Model {

        fun getGoldGiftOutlayList(current: String?, size: String?, date: String?): HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult?>?>?>?>?

    }
}