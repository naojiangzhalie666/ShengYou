package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;

public interface MaterialContract {

    interface View extends BaseView {

        void onSuccess(MyFamilyResult familyResult);

        void onError(String msg);

    }

    interface Presenter {

        void getMyFamily(String userId);
    }

    interface Model{
        /**
         * 根据userid查询家族信息
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId);
    }

}
