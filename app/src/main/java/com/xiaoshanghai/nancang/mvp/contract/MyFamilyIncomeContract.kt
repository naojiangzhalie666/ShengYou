package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult
import com.xiaoshanghai.nancang.net.bean.UserIncome
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface MyFamilyIncomeContract {

    interface View:BaseView{

        /**
         * 设置时间
         *
         * @param date
         */
        fun setTime(date: String?)

        /**
         * 刷新成功
         * @param refresh
         * @param incomeList
         */
        fun refreshSuccess(refresh: RefreshLayout?, incomeList: MutableList<UserIncome>?)

        /**
         * 加载成功
         * @param refresh
         * @param incomeList
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, incomeList: MutableList<UserIncome>?)

        fun onFamilySuccess(fammilyResult:MyFamilyResult?)

        fun onError(msg:String?)

    }

    interface Presenter {

        /**
         * 时间选择
         */
        fun selectTime(date: String?)


        fun getFamilyIncome(refresh: RefreshLayout?,userId: String?,clanId:String?,startTime:String?)

        fun getMyFamilyInfo(userId:String?)

    }

    interface Model {

        fun getFamilyIncome(current: String?, size: String?,userId: String?,clanId:String?,startTime:String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<UserIncome>>>>

        fun getFamilyInfo(userId: String?): HttpObservable<BaseResponse<MyFamilyResult>>

    }

}