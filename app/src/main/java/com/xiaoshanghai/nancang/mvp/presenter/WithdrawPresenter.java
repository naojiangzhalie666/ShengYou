package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.WithdrawContract;
import com.xiaoshanghai.nancang.mvp.model.WithdrawModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BindEntity;
import com.xiaoshanghai.nancang.net.bean.NewGoldResult;

import java.util.List;

public class WithdrawPresenter extends BasePresenter<WithdrawContract.View> implements  WithdrawContract.Presenter{

    private WithdrawModel model;

    public WithdrawPresenter() {

        model = new WithdrawModel();

    }

    @Override
    public void getCashList() {
                model.getCashList()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<NewGoldResult>>(){

                    @Override
                    protected void success(List<NewGoldResult> bean, BaseResponse<List<NewGoldResult>> response) {
                        List<NewGoldResult> data = response.getData();
                        getView().cashSuccess(data);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

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

    @Override
    public void withdraw(String cashConfigId) {
        getView().showLoading();
        model.withdraw(cashConfigId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().onWithdrawSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onWithdrawError(msg);
                    }
                });
    }

    @Override
    public void getMyDiamondNum() {
        model.getMyDiamondNum()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Double>(){

                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        getView().onDiamondSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
