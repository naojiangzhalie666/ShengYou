package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FriendsSelectionContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class FriendsSelectionModel implements FriendsSelectionContract.Model {
    @Override
    public HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId) {
        return HttpClient.getApi().buyDeck(deckId, userId);
    }
}
