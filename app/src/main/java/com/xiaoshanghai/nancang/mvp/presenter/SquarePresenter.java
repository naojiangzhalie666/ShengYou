package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.SquareConstract;
import com.xiaoshanghai.nancang.mvp.model.SquareModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.TopicMsg;

import java.util.List;

public class SquarePresenter extends BasePresenter<SquareConstract.View> implements SquareConstract.Presenter {

    private SquareModel model;

    public SquarePresenter() {
        model = new SquareModel();
    }

    @Override
    public void getTopicMsg(String userId) {
        model.getTopicMsg("1","1",userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<HomeRoomResult<List<TopicMsg>>>(){

                    @Override
                    protected void success(HomeRoomResult<List<TopicMsg>> bean, BaseResponse<HomeRoomResult<List<TopicMsg>>> response) {
                        getView().onTopicMsgSuccess(bean.getRecords());
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);

                    }
                });
    }
}
