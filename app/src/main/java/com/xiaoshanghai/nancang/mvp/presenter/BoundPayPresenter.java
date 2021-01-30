package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.BoundPayContract;
import com.xiaoshanghai.nancang.mvp.model.BoundPayModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BindEntity;

public class BoundPayPresenter extends BasePresenter<BoundPayContract.View> implements BoundPayContract.Presenter {

    private BoundPayModel model = new BoundPayModel();

    @Override
    public void getBoundPay() {
        model.getBoundPay()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<BindEntity>(){

                    @Override
                    protected void success(BindEntity bean, BaseResponse<BindEntity> response) {
                        getView().onBoundSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onBoundError(msg);
                    }
                });
    }
}
