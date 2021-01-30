package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.AttentionSelectContract;
import com.xiaoshanghai.nancang.mvp.model.AttentionSelectModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class AttentionSelectPresenter extends BasePresenter<AttentionSelectContract.View> implements AttentionSelectContract.Presenter {

    private AttentionSelectModel model;

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public AttentionSelectPresenter() {
        model = new AttentionSelectModel();
    }

    @Override
    public void refreshFollow(RefreshLayout refresh) {
        model.getMyFollow(mPage + "", size + "")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<MyFollowResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<MyFollowResult>> bean, BaseResponse<HomeRoomResult<List<MyFollowResult>>> response) {
                        List<MyFollowResult> records = bean.getRecords();

                            if (mPage == initPage) {
                                getView().refreshSuccess(refresh, records);
                            } else {
                                getView().loadMoreSuccess(refresh, records);
                            }
                            if (records.size() > 0)
                                mPage++;

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
}
