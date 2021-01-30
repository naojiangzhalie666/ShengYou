package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.TopicMsg;

import java.util.List;

public interface SquareConstract {

    interface View extends BaseView{

        void onTopicMsgSuccess(List<TopicMsg> topicMsgs);

        void onError(String msg);
    }

    interface Presenter {

        void getTopicMsg(String userId);

    }

    interface Model {
        /**
         * 话题列表
         * @param current
         * @param size
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<TopicMsg>>>> getTopicMsg(String current,String size,String userId);
    }
}
