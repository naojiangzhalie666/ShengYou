package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface FansContract {
    interface View extends BaseView {
        /**
         * 请求粉丝列表刷新成功
         *
         * @param myFollows
         */
        void refreshSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows);

        /**
         * 请求粉丝列表加载成功
         *
         * @param refresh
         * @param myFollows
         */
        void loadMoreSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows);

        /**
         * 关注成功
         * @param status
         * @param position
         */
        void followSuccess(String status ,int position);

        /**
         * 取消成功
         * @param status
         * @param position
         */
        void unFollowSuccess(String status,int position);

        /**
         * 接口请求失败
         *
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {

        /**
         * 获取粉丝列表
         *
         * @param refreshLayout
         */
        void getMyFans(RefreshLayout refreshLayout);

        /**
         * 关注
         *
         * @param userId
         */
        void follow(String userId, int position);

        /**
         * 取消关注
         *
         * @param user
         */
        void unFollow(String user, int position);

    }

    interface Model {
        /**
         * 获取粉丝列表
         *
         * @param current
         * @param size
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFans(String current, String size);

        /**
         * 关注 & 取消关注
         *
         * @param userId
         * @param status
         * @return
         */
        HttpObservable<BaseResponse<String>> follow(String userId, String status);

    }

}


