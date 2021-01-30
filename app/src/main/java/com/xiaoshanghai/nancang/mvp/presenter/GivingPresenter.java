package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.GivingContract;
import com.xiaoshanghai.nancang.mvp.model.GivingModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GiftRecordResult;

public class GivingPresenter extends BasePresenter<GivingContract.View> implements GivingContract.Presenter {

    private GivingModel model;

    public GivingPresenter() {
        model = new GivingModel();
    }

    @Override
    public void giftRecord(String userId) {
        model.giftRecord(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<GiftRecordResult>() {
                    @Override
                    protected void success(GiftRecordResult bean, BaseResponse<GiftRecordResult> response) {
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
