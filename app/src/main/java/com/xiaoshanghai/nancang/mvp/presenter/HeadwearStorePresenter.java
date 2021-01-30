package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.HeadwearStoreContract;
import com.xiaoshanghai.nancang.mvp.model.HeadoearStoreModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.net.bean.MineReslut;

import java.util.List;

public class HeadwearStorePresenter extends BasePresenter<HeadwearStoreContract.View> implements HeadwearStoreContract.Presenter {

    private HeadoearStoreModel model;

    public HeadwearStorePresenter() {
        model = new HeadoearStoreModel();
    }

    @Override
    public void getStoreHeadwear() {
        model.store(1)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<Decks>>() {
                    @Override
                    protected void success(List<Decks> bean, BaseResponse<List<Decks>> response) {
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);

                    }
                });
    }

    @Override
    public void giveAway(String deckId, String userId,Decks decks,MineReslut mResult) {
        getView().showLoading();
        model.buyDeck(deckId,userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().giveAwaySuccess(bean,decks,mResult);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
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
