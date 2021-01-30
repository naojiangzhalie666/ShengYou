package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.FamilyRecordContract;
import com.xiaoshanghai.nancang.mvp.model.FamilyRecordModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.RecordResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class FamilyRecordPresenter extends BasePresenter<FamilyRecordContract.View> implements FamilyRecordContract.Presenter {

    private FamilyRecordModel model;
    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public FamilyRecordPresenter() {
        model = new FamilyRecordModel();
    }

    @Override
    public void getRecord(RefreshLayout refreshLayout, String userId) {
        model.getapplyList(userId, mPage + "", size + "")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<RecordResult>>>() {

                    @Override
                    protected void success(HomeRoomResult<List<RecordResult>> bean, BaseResponse<HomeRoomResult<List<RecordResult>>> response) {

                        List<RecordResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refresh(refreshLayout, records);
                            } else {
                                getView().loadMore(refreshLayout, records);
                            }
                            if (records.size() > 0)
                                mPage++;
                        }
                    }


                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout, msg);

                    }
                });
    }
}
