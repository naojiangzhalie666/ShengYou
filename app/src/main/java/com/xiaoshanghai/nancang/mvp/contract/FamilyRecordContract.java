package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.RecordResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface FamilyRecordContract {

    interface View extends BaseView {

        /**
         * 刷新数据
         *
         * @param refreshLayout
         */
        void refresh(RefreshLayout refreshLayout, List<RecordResult> roomResults);

        /**
         * 加载更多
         *
         * @param refreshLayout
         */
        void loadMore(RefreshLayout refreshLayout, List<RecordResult> roomResults);

        /**
         * 接口请求失败
         *
         * @param refreshLayout
         * @param msg
         */
        void onError(RefreshLayout refreshLayout, String msg);

    }

    interface Presenter {
        /**
         * 获取数据
         *
         * @param refreshLayout
         */
        void getRecord(RefreshLayout refreshLayout, String userId);
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<RecordResult>>>> getapplyList(String userId, String begin, String size);
    }
}
