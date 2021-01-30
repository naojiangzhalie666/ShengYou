package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BindEntity;

public interface BoundPayContract {

    interface View extends BaseView {

        void onBoundSuccess(BindEntity result);

        void onBoundError(String msg);

    }

    interface Presenter {

        void getBoundPay();

    }

    interface Model {

        HttpObservable<BaseResponse<BindEntity>> getBoundPay();
    }

}
