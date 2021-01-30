package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GiftRecordResult;

public interface GivingContract {

    interface View extends BaseView {

        /**
         * 获取礼物接口成功回调
         * @param result
         */
        void onSuccess(GiftRecordResult result);

        /**
         * 接口请求错误回调
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 礼物记录
         * @param userId
         */
        void giftRecord(String userId);

    }

    interface Model {

        /**
         * 获取礼物请求
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<GiftRecordResult>> giftRecord(String userId);

    }

}
