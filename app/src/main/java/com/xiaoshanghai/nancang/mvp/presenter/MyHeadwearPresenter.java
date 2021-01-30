package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MyHeadwearContract;
import com.xiaoshanghai.nancang.mvp.model.MyHeadwearModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

public class MyHeadwearPresenter extends BasePresenter<MyHeadwearContract.View> implements MyHeadwearContract.Presenter {

    private MyHeadwearModel model;

    public MyHeadwearPresenter() {
        model = new MyHeadwearModel();
    }

    @Override
    public void getMyHeadwear(RefreshLayout refreshLayout) {

        model.getMyHeadwear()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<DeckResult>() {
                    @Override
                    protected void success(DeckResult bean, BaseResponse<DeckResult> response) {
                        getView().onSuccess(bean, refreshLayout);
                    }

                    @Override
                    protected void error(String msg) {
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        getView().myHeadwearError(msg);
                    }
                });
    }

    @Override
    public void useHeadwear(Decks decks) {
        if (decks == null) return;
        model.useHeadwear(decks.getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().useHeadwearSuccess(decks, bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().equals(msg);
                    }
                });
    }

    @Override
    public void buyDeck(String deckId) {
        getView().showLoading();
        model.buyDeck(deckId,"")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().buyDeckSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }


}
