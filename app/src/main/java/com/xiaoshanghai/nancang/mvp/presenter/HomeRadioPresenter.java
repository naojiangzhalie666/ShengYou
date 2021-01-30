package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.HomeRadioContract;
import com.xiaoshanghai.nancang.mvp.model.HomeReadioModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.RoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class HomeRadioPresenter extends BasePresenter<HomeRadioContract.View> implements HomeRadioContract.Presenter {

    private HomeReadioModel model;

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public HomeRadioPresenter() {
        model = new HomeReadioModel();
    }

    @Override
    public void getRooms(RefreshLayout refreshLayout, String tpeId) {
        model.getRooms(mPage + "", size + "", tpeId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<RoomResult>>>() {

                    @Override
                    protected void success(HomeRoomResult<List<RoomResult>> bean, BaseResponse<HomeRoomResult<List<RoomResult>>> response) {

                        List<RoomResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refreshRoom(refreshLayout, records);
                            } else {
                                getView().loadMoreRoom(refreshLayout, records);
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
