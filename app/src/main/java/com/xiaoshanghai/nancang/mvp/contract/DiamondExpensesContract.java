package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.IncomeResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface DiamondExpensesContract {

    interface View extends BaseView {

        void onError(String msg);

        /**
         * 设置时间
         *
         * @param date
         */
        void setTime(String date);

        /**
         * 刷新成功
         * @param refresh
         * @param incomeList
         */
        void refreshSuccess(RefreshLayout refresh, List<IncomeResult> incomeList);

        /**
         * 加载成功
         * @param refresh
         * @param incomeList
         */
        void loadMoreSuccess(RefreshLayout refresh, List<IncomeResult> incomeList);

    }

    interface Presenter {
        /**
         * 时间选择
         */
        void selectTime(String date);

        /**
         * 获取收入列表
         * @param date
         */
        void getIncomeList(RefreshLayout refresh,String date);
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getIncomeList(String current, String size, String date);

    }

}
