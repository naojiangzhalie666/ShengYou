package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MyCarContract;
import com.xiaoshanghai.nancang.mvp.model.MyCarModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

public class MyCarPresenter extends BasePresenter<MyCarContract.View> implements MyCarContract.Presenter {

    private MyCarModel model;

    public MyCarPresenter() {
        model = new MyCarModel();
    }

    @Override
    public void getMyCar(RefreshLayout refreshLayout) {
        model.getMyCar()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<DeckResult>() {
                    @Override
                    protected void success(DeckResult bean, BaseResponse<DeckResult> response) {
                        if (refreshLayout!=null) refreshLayout.finishRefresh();
                        if (bean!=null) {
                            getView().onCarSuccess(bean.getDecks());
                        } else {
                            getView().onCarError("");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        if (refreshLayout!=null) refreshLayout.finishRefresh();
                        getView().onCarError(msg);
                    }
                });
    }

    @Override
    public void useCar(Decks decks) {
        if (decks == null) return;
        model.useCar(decks.getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().useCarSuccess(decks,bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });

    }

}
