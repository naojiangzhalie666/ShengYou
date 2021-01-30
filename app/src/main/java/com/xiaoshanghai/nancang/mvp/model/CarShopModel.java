package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.CarShopContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.Decks;

import java.util.List;

public class CarShopModel implements CarShopContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<Decks>>> store(Integer deckType) {
        return HttpClient.getApi().store(deckType);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId) {
        return HttpClient.getApi().buyDeck(deckId, userId);
    }
}
