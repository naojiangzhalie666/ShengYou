package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.BindBlackContract;
import com.xiaoshanghai.nancang.mvp.model.BindBlackModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class BindBlackPresenter extends BasePresenter<BindBlackContract.View> implements BindBlackContract.Presenter{

    private BindBlackModel model = new BindBlackModel();

    @Override
    public void getCode(String phone) {
        model.getStatusCode(phone)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            getView().codeSuccess();
                        } else {
                            getView().codeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().codeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                    }
                });
    }

    @Override
    public void bindBlackNum(String account, String bankType, String bankName, String realName, String code) {
        getView().showLoading();
        model.bindBlackNum("1",account,bankType,bankName,realName,code)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().onBindSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onBindError(msg);
                    }
                });
    }

}
