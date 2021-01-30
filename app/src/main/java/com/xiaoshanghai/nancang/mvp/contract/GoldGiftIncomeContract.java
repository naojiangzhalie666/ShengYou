package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface GoldGiftIncomeContract {

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
         *
         * @param refresh
         * @param incomeList
         */
        void refreshSuccess(RefreshLayout refresh, List<GoldGiftResult> incomeList);

        /**
         * 加载成功
         *
         * @param refresh
         * @param incomeList
         */
        void loadMoreSuccess(RefreshLayout refresh, List<GoldGiftResult> incomeList);

    }

    interface Presenter {
        /**
         * 时间选择
         */
        void selectTime(String date);

        /**
         * 获取收入列表
         *
         * @param date
         */
        void getGoldGiftList(RefreshLayout refresh, String date);
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getGoldGiftList(String current, String size, String date);

    }

}
