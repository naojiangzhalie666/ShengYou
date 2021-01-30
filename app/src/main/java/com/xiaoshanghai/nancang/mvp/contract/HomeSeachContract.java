package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeSeachEntity;
import com.xiaoshanghai.nancang.net.bean.SeachRoomEntty;

import java.util.List;

public interface HomeSeachContract {

    interface View extends BaseView {

        void onRecordResult(List<SeachRoomEntty> result);

        void onDeleteSuccess(String status);

        void onSeachSuccess(List<HomeSeachEntity> seachResult);

        void onSeachError(String msg);

        void onError(String msg);

    }

    interface Presenter {

        void getSeachRoomRecord();

        void removeEnterRoomRecord(String userId);

        void seach(String searchString);

    }

    interface Model {

        HttpObservable<BaseResponse<List<SeachRoomEntty>>> getSeachRoomRecord();

        HttpObservable<BaseResponse<String>> removeEnterRoomRecord(String userId);

        HttpObservable<BaseResponse<List<HomeSeachEntity>>> getSeachResult(String searchString);
    }
}
