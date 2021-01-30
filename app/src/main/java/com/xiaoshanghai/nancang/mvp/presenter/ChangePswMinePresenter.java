package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.ChangePswMineContract;
import com.xiaoshanghai.nancang.mvp.model.ChangePswMineModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class ChangePswMinePresenter extends BasePresenter<ChangePswMineContract.View> implements ChangePswMineContract.Presenter {


    private final ChangePswMineModel mModel;

    public ChangePswMinePresenter() {
        mModel = new ChangePswMineModel();
    }

    @Override
    public void changePsw(String type, String pswNew, String smsCode, String pswOld) {
        getView().showLoading();
        mModel.changePswMine(type, pswNew, smsCode, pswOld)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {


                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        if (bean != null) {
                            switch (bean) {
                                case 1:
                                    getView().changeSuccess();
                                    break;
                                case 2:
                                    getView().changeError("验证码错误");
                                    break;
                                case 3:
                                    getView().changeError("旧密码错误");
                                    break;
                                default:
                                    getView().changeError("");
                                    break;
                            }
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().changeError(msg);
                    }
                });
    }

    @Override
    public void getCode(String phone) {

    }
}
