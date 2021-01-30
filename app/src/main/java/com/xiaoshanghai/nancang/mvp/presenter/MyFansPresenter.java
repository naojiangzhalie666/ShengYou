package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MyFansContract;
import com.xiaoshanghai.nancang.mvp.model.MyFansModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class MyFansPresenter extends BasePresenter<MyFansContract.View> implements MyFansContract.Presenter {

    private MyFansModel model;

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public MyFansPresenter() {
        model = new MyFansModel();
    }

    @Override
    public void getMyFans(RefreshLayout refresh) {
        model.getMyFans(mPage + "", size + "")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<MyFollowResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<MyFollowResult>> bean, BaseResponse<HomeRoomResult<List<MyFollowResult>>> response) {
                        List<MyFollowResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refreshSuccess(refresh, records);
                            } else {
                                getView().loadMoreSuccess(refresh, records);
                            }
                            if (records.size() > 0)
                                mPage++;
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        if (refresh != null) {
                            refresh.finishRefresh();
                            refresh.finishLoadMore();
                        }
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void follow(String userId, int position) {
        getView().showLoading();
        model.follow(userId,"1")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().followSuccess(bean,position);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);

                    }
                });

    }

    @Override
    public void unFollow(String userId, int position) {
        getView().showLoading();
        model.follow(userId,"0")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().unFollowSuccess(bean,position);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);

                    }
                });

    }

}
