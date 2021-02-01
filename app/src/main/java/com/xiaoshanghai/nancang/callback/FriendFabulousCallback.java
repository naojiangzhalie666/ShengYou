package com.xiaoshanghai.nancang.callback;

import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;

public interface FriendFabulousCallback {

    /**
     * 点赞回调
     *
     * @param result
     */
    void onClickFabulous(FriendsCircleResult result, int position);

    /**
     * 点击留言回调
     *
     * @param result
     */
    void onClickComment(FriendsCircleResult result, int position);

    /**
     * 点击分享回调
     *
     * @param result
     */
    void onClickShare(FriendsCircleResult result, int position);

    /**
     * 点击头像回调
     * @param result
     * @param position
     */
    void onClickAvater(FriendsCircleResult result, int position);

    /**
     * 点击更多回调
     * @param result
     * @param position
     */
    void onClickMore(FriendsCircleResult result, int position);

    /**
     * 私信聊天
     */
    void  onClickChat(FriendsCircleResult result, int position);
    /**
     * 视频
     */
    void  onClickVideo(FriendsCircleResult result, int position);

}
