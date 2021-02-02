package com.xiaoshanghai.nancang.mvp.contract;

import android.content.Context;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface RecommendContract {

    interface View extends BaseView {

        /**
         * 刷新数据
         *
         * @param refreshLayout
         */
        void refresh(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults);

        /**
         * 加载更多
         *
         * @param refreshLayout
         */
        void loadMore(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults);

        /**
         * 获取数据失败
         *
         * @param refreshLayout
         * @param msg
         */
        void onError(RefreshLayout refreshLayout, String msg);

        /**
         * 点赞成功
         *
         * @param msg
         */
        void fabulousSuccess(String msg);


        /**
         * 删除话题成功
         *
         * @param status
         */
        void onDeleteSuccess(String status,FriendsCircleResult result);

    }

    interface Presenter {

        /**
         * 获取所有朋友圈数据数据
         *
         * @param refreshLayout
         */
        void getFriendsCircle(RefreshLayout refreshLayout, String city, Context context);

        /**
         * 获取关注朋友圈数据数据
         *
         * @param refreshLayout
         */
        void getAttention(RefreshLayout refreshLayout);

        /**
         * 点赞/取消
         *
         * @param topicId 朋友圈ID
         */
        void fabulous(String topicId);

        /**
         * 删除话题
         *
         * @param topicId
         */
        void deleteTopic(String topicId,FriendsCircleResult result);
    }

    interface Model {
        /**
         * 获取所有朋友圈
         * @param current
         * @param size
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size,String userId,String city);

        HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size,String userId);
        /**
         * 获取关注朋友圈
         * @param current
         * @param size
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getAttention(String current, String size);

        /**
         * 点赞/取消
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<String>> fabulous(String topicId);

        /**
         * 删除话题
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<String>> deleteTopic(String topicId);
    }

}
