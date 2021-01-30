package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.FamilySquareContract;
import com.xiaoshanghai.nancang.mvp.model.FamilySquareModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FamilyRankResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class FamilySquarePresenter extends BasePresenter<FamilySquareContract.View> implements FamilySquareContract.Presenter {

    private  FamilySquareModel model;
    public FamilySquarePresenter() {
        model = new FamilySquareModel();
    }

    @Override
    public void getFamilyRank(RefreshLayout refreshLayout) {
        model.getFamilyRank()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<FamilyRankResult>>() {
                    @Override
                    protected void success(List<FamilyRankResult> bean, BaseResponse<List<FamilyRankResult>> response) {
                        getView().familyRankSuccess(bean,refreshLayout);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout,msg);
                    }
                });
    }
}
