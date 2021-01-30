package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FamilyRankResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface FamilySquareContract {


    interface View extends BaseView {

        /**
         * 成功获取家族魅力榜
         *
         * @param results
         */
        void familyRankSuccess(List<FamilyRankResult> results,RefreshLayout refreshLayout);

        /**
         * 请求失败
         * @param refreshLayout
         */
        void onError(RefreshLayout refreshLayout,String msg);

    }

    interface Presenter {
        /**
         * 获取家族魅力排行榜
         *
         * @return
         */
        void getFamilyRank(RefreshLayout refreshLayout);
    }

    interface Model {
        /**
         * 获取家族魅力排行榜
         *
         * @return
         */
        HttpObservable<BaseResponse<List<FamilyRankResult>>> getFamilyRank();
    }
}
