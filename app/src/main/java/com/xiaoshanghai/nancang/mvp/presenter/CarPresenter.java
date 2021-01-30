package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.CarContract;
import com.xiaoshanghai.nancang.mvp.model.CarModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;

public class CarPresenter extends BasePresenter<CarContract.View> implements CarContract.Presenter {

    private CarModel model;

    public CarPresenter() {
        model = new CarModel();
    }

    @Override
    public void carRecord(String userId) {
        model.carRecord(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<DeckResult>() {
                    @Override
                    protected void success(DeckResult bean, BaseResponse<DeckResult> response) {
                        getView().carSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
