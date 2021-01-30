package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MyHeadwearContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;

public class MyHeadwearModel implements MyHeadwearContract.Model {
    @Override
    public HttpObservable<BaseResponse<DeckResult>> getMyHeadwear() {
        return HttpClient.getApi().getMyHeadwear();
    }

    @Override
    public HttpObservable<BaseResponse<String>> useHeadwear(String id) {
        return HttpClient.getApi().useHeadwear(id);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId) {
        return HttpClient.getApi().buyDeck(deckId, userId);
    }
}
