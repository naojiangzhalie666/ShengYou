package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;


public interface BuddyContract {

    interface View extends BaseView {

        /**
         * 请求好友列表刷新成功
         *
         * @param myFollows
         */
        void refreshSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows);

        /**
         * 请求好友列表加载成功
         *
         * @param refresh
         * @param myFollows
         */
        void loadMoreSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 获取朋友列表
         *
         * @param refreshLayout
         */
        void getMyFriends(RefreshLayout refreshLayout);

    }

    interface Model {
        /**
         * 获取好友列表
         * @param current
         * @param size
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFriends(String current, String size);
    }

}
