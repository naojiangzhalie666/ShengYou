package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.FamilyContract;
import com.xiaoshanghai.nancang.mvp.model.FamilyModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class FamilyPresenter extends BasePresenter<FamilyContract.View> implements FamilyContract.Presenter {

    private FamilyModel model;

    public FamilyPresenter() {
        model = new FamilyModel();
    }

    @Override
    public void getFamilyInfo(String userId, RefreshLayout refreshLayout) {
        getView().showLoading();
        model.getFamilyInfo(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MyFamilyResult>() {
                    @Override
                    protected void success(MyFamilyResult result, BaseResponse<MyFamilyResult> response) {
                        getView().familySuccess(result, refreshLayout);

                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg, refreshLayout);
                    }
                });
    }

    @Override
    public void getStartRecommend(RefreshLayout refreshLayout) {
        model.getStartRecommend()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<StartRecommendResult>>() {
                    @Override
                    protected void success(List<StartRecommendResult> bean, BaseResponse<List<StartRecommendResult>> response) {
                        getView().hideLoading();
                        getView().recommendSuccess(bean, refreshLayout);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg, refreshLayout);
                    }
                });
    }
}
