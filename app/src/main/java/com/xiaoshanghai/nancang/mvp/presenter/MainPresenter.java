package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MainContract;
import com.xiaoshanghai.nancang.mvp.model.MainModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainModel model = new MainModel();

    @Override
    public void exitRoom(String roomId) {
        model.exitRoom(roomId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {

                    }

                    @Override
                    protected void error(String msg) {

                    }
                });
    }

    @Override
    public void getTeensStatus(String userId) {
        model.getTeensStatus(userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<String>(){

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().teenSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
