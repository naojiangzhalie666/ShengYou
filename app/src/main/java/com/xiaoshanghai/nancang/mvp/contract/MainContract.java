package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface MainContract {

    interface View extends BaseView {

        void teenSuccess(String status);

        void onError(String msg);

    }

    interface Presenter {

        /**
         * 退出房间
         */
        void exitRoom(String roomId);

        void getTeensStatus(String userId);
    }

    interface Model {

        HttpObservable<BaseResponse<Integer>> exitRoom(String roomId);

        /**
         * 青少年模式
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> getTeensStatus(String userId);

    }
}
