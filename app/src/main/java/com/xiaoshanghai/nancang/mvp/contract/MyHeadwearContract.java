package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.scwang.smartrefresh.layout.api.RefreshLayout;


public interface MyHeadwearContract {

    interface View extends BaseView{

        /**
         *  获取头饰列表接口请求成功
         * @param result
         */
        void onSuccess(DeckResult result,RefreshLayout refreshLayout);

        /**
         * 使用头饰
         * @param decks
         * @param data
         */
        void useHeadwearSuccess(Decks decks,String data);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);

        /**
         *  获取头饰列表接口请求失败
         * @param
         */
        void myHeadwearError(String mgs);

        /**
         * 购买接口请求成功
         * @param status
         */
        void buyDeckSuccess(Integer status);
    }

    interface Presenter {

        /**
         * 获取头饰列表
         * @param refreshLayout
         */
        void getMyHeadwear(RefreshLayout refreshLayout);

        /**
         * 使用头饰
         * @param decks
         */
        void useHeadwear(Decks decks);

        /**
         * 购买头饰或座驾
         * @param deckId
         */
        void buyDeck(String deckId);


    }

    interface Model {
        /**
         * 获取我的头饰
         * @return
         */
        HttpObservable<BaseResponse<DeckResult>> getMyHeadwear();

        /**
         * 使用头饰
         * @param id
         * @return
         */
        HttpObservable<BaseResponse<String>> useHeadwear(String id);

        /**
         * 购买头饰/座驾
         *
         * @param deckId
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId);


    }

}
