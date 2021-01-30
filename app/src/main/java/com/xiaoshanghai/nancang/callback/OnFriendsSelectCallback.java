package com.xiaoshanghai.nancang.callback;

import com.xiaoshanghai.nancang.net.bean.MyFollowResult;

public interface OnFriendsSelectCallback {
    void onFriendsSelect(MyFollowResult result);
    void onFriendsClick(MyFollowResult result);
}
