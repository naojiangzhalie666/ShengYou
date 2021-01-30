package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;


public interface CashRecordContract {

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
        void refreshSuccess(RefreshLayout refresh, List<CashEntity> incomeList);

        /**
         * 加载成功
         * @param refresh
         * @param incomeList
         */
        void loadMoreSuccess(RefreshLayout refresh, List<CashEntity> incomeList);

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
        void getIncomeList(RefreshLayout refresh,String userId,String date);

    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getCashList(String current,String size,String userId,String date);

    }
}
