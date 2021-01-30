package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface MyGoldContract {

    interface View extends BaseView {
        /**
         * 获取到金币数量
         * @param goldNum
         */
        void onSuccess(Double goldNum);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {
        /**
         * 获取金币数量
         */
        void getMyGoldNum();
    }

    interface Model {
        HttpObservable<BaseResponse<Double>> getMyGoldNum();
    }
}
