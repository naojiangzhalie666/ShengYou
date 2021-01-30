package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.BindAliContract;
import com.xiaoshanghai.nancang.mvp.model.BindAliModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class BindAliPresenter extends BasePresenter<BindAliContract.View> implements BindAliContract.Presenter {

    private BindAliModel model = new BindAliModel();

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
    public void bindWeChat(String account, String realName, String code) {
        model.bindWeChat("2",account,realName,code)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onWeChatSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onWeChatError(msg);
                    }
                });
    }


}
