package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.SystemNotic;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;

import java.util.List;

public interface NewsContract {

    interface View extends BaseView {

        void onSystemNoicSuccess(List<SystemNotic> notics);

        void onError(String msg);

    }

    interface Presenter {

       void initIM(ConversationLayout mConversationLayout);

       void getSystemNoic();
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<SystemNotic>>>> getSystemNotic( String current,String size);

    }

}
