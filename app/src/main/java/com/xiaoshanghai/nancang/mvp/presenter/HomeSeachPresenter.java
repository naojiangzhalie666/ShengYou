package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.HomeSeachContract;
import com.xiaoshanghai.nancang.mvp.model.HomeSeachModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeSeachEntity;
import com.xiaoshanghai.nancang.net.bean.SeachRoomEntty;

import java.util.List;

public class HomeSeachPresenter extends BasePresenter<HomeSeachContract.View> implements HomeSeachContract.Presenter {

    private HomeSeachModel model = new HomeSeachModel();

    @Override
    public void getSeachRoomRecord() {
        model.getSeachRoomRecord()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<List<SeachRoomEntty>>(){

                    @Override
                    protected void success(List<SeachRoomEntty> bean, BaseResponse<List<SeachRoomEntty>> response) {
                        getView().onRecordResult(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void removeEnterRoomRecord(String userId) {
        model.removeEnterRoomRecord(userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<String>(){

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().onDeleteSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void seach(String searchString) {
        model.getSeachResult(searchString)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<HomeSeachEntity>>() {
                    @Override
                    protected void success(List<HomeSeachEntity> bean, BaseResponse<List<HomeSeachEntity>> response) {
                        getView().onSeachSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onSeachError(msg);
                    }
                });
    }
}
