package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface MyCarContract {

    interface View extends BaseView {

        /**
         * 获取座驾列表成功
         * @param result
         */
        void onCarSuccess(List<Decks> result);

        /**
         * 使用头饰
         * @param decks
         * @param data
         */
        void useCarSuccess(Decks decks,String data);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);

        /**
         * 请求获取座驾接口失败
         * @param msg
         */
        void onCarError(String msg);

    }

    interface Presenter {

        /**
         * 获取座驾列表
         * @param refreshLayout
         */
        void getMyCar(RefreshLayout refreshLayout);

        /**
         * 请求使用头饰接口
         */
        void useCar(Decks decks);


    }

    interface Model {

        /**
         * 获取座驾
         * @return
         */
        HttpObservable<BaseResponse<DeckResult>> getMyCar();

        /**
         * 使用座驾
         * @param id
         * @return
         */
        HttpObservable<BaseResponse<String>> useCar( String id);

    }

}
