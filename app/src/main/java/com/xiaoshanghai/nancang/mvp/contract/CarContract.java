package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;

public interface CarContract  {

    interface View extends BaseView {

        /**
         * 获取历史接口请求成功
         * @param deckResult
         */
        void carSuccess(DeckResult deckResult);

        /**
         * 请求失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 请求获取座驾历史
         * @param userId
         */
        void carRecord(String userId);

    }

    interface Model {
        /**
         * 个人资料·座驾列表
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<DeckResult>> carRecord(String userId);
    }

}
