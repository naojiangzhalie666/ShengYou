package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MyGoldContract;
import com.xiaoshanghai.nancang.mvp.model.MyGoldModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MyGoldPresenter extends BasePresenter<MyGoldContract.View> implements MyGoldContract.Presenter{

    private MyGoldModel model;

    public MyGoldPresenter() {
        model = new MyGoldModel();
    }

    @Override
    public void getMyGoldNum() {
        model.getMyGoldNum()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        if (bean!=null) {
                            getView().onSuccess(bean);
                        } else {
                            getView().onError("请求错误");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
