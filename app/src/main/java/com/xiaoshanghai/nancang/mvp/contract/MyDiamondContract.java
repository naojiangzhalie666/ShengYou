package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface MyDiamondContract {

    interface View extends BaseView {

        /**
         * 成功获取钻石数量
         * @param diamondNum
         */
        void onDiamondSuccess(Double diamondNum);

        /**
         * 请求接口失败
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {

        void replace(BaseMvpFragment fragment);

        /**
         * 获取钻石数量
         */
        void getMyDiamondNum();

    }

    interface Model {
        /**
         * 获取钻石数量
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyDiamondNum();
    }
}
