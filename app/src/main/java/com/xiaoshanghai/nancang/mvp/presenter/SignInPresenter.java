package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.SignInContract;
import com.xiaoshanghai.nancang.mvp.model.SignInModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.SignDayEntity;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;

import java.util.List;

public class SignInPresenter extends BasePresenter<SignInContract.View> implements SignInContract.Presenter {

    private SignInModel model = new SignInModel();

    @Override
    public void getSignReward() {
        model.getSignReward()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<List<SignRewardEntity>>(){

                    @Override
                    protected void success(List<SignRewardEntity> bean, BaseResponse<List<SignRewardEntity>> response) {
                        getView().onSignRewardSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void getSignDay() {
        model.getSignDay()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<SignDayEntity>(){

                    @Override
                    protected void success(SignDayEntity bean, BaseResponse<SignDayEntity> response) {
                        getView().onSignDaySuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void sign() {
        model.sign()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onSginSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
