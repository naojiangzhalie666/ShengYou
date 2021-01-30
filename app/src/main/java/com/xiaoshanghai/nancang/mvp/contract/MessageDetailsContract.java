package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.CommentResult;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;


public interface MessageDetailsContract {

    interface View extends BaseView {

        void onFriendSuccess(FriendsCircleResult result);

        /**
         * 刷新成功
         *
         * @param refreshLayout
         * @param records
         */
        void refresh(RefreshLayout refreshLayout, List<CommentResult> records, String commentNum);

        /**
         * 加载成功
         *
         * @param refreshLayout
         * @param records
         */
        void loadMore(RefreshLayout refreshLayout, List<CommentResult> records);

        /**
         * 请求失败
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
         * 一级评论成功
         *
         * @param msg
         */
        void commentSuccess(String msg);

        /**
         * 二级评论成功
         *
         * @param commentId
         * @param position
         */
        void secondCommentSuccess(String commentId, int position);

        /**
         * 查询成功
         *
         * @param result
         * @param position
         */
        void secondSuccess(CommentResult result, int position);

    }

    interface Presenter {

        void getFriend(String topicId);

        /**
         * 获取评论
         *
         * @param topicId
         */
        void getComment(RefreshLayout refreshLayout, String topicId);

        /**
         * 点赞/取消
         *
         * @param topicId 朋友圈ID
         */
        void fabulous(String topicId);

        /**
         * 朋友圈评论 一级评论和二级品论
         *
         * @param topicId        朋友圈id
         * @param commentContent 评论文本内容
         * @param commentType    品论类型： 1. 一级品论 2.二级评论 二级评论时需要提供评论目标的id
         * @param fatherId       评论目标id 当为二级评论时必须传此参数
         * @param position       二级评论时的position
         */
        void comment(String topicId, String commentContent, String commentType, String fatherId, int position);

        /**
         * 查询单个评论含二级评论
         *
         * @param commentId
         * @param position
         */
        void getSecondComment(String commentId, int position);

    }

    interface Model {
        /**
         * 获取评论
         *
         * @param current
         * @param size
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<CommentResult>>>> getComment(String current, String size, String topicId);

        /**
         * 点赞/取消
         *
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<String>> fabulous(String topicId);

        /**
         * 评论朋友圈
         *
         * @param topicId
         * @param commentContent
         * @param commentType
         * @param fatherId
         * @return
         */
        HttpObservable<BaseResponse<String>> comment(String topicId, String commentContent, String commentType, String fatherId);

        /**
         * 查询单个评论含二级评论
         *
         * @param commentId
         * @return
         */
        HttpObservable<BaseResponse<CommentResult>> getSecondComment(String commentId);

        /**
         * 获取单个朋友圈
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<FriendsCircleResult>> getFriendOne(String topicId);
    }
}
