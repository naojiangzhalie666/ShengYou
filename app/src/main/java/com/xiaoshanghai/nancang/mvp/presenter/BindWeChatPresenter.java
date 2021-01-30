package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.BindWeChatContract;
import com.xiaoshanghai.nancang.mvp.model.BindWeChatModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class BindWeChatPresenter extends BasePresenter<BindWeChatContract.View> implements BindWeChatContract.Presenter {

    private BindWeChatModel model = new BindWeChatModel();

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
    public void bindWeChat( String account, String realName, String code) {
        getView().showLoading();
        model.bindWeChat("3", account, realName, code)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().onWeChatSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onWeChatError(msg);
                    }
                });
    }

}
