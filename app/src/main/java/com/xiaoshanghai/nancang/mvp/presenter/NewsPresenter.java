package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Intent;
import android.view.View;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.helper.ConversationLayoutHelper;
import com.xiaoshanghai.nancang.mvp.contract.NewsContract;
import com.xiaoshanghai.nancang.mvp.model.NewsModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ChatActivity;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.NewsFragment;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.SystemNotic;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;

import java.util.List;

public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {

    private NewsModel model;

    public NewsPresenter() {
        model = new NewsModel();
    }

    @Override
    public void initIM(ConversationLayout mConversationLayout) {

        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        mConversationLayout.getTitleBar().setVisibility(View.GONE);
        // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
        ConversationLayoutHelper.customizeConversation(mConversationLayout);
        mConversationLayout.getConversationList().setOnItemClickListener((view, position, conversationInfo) -> {
            //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
            startChatActivity(conversationInfo);
        });
        mConversationLayout.getConversationList().setOnItemLongClickListener((view, position, conversationInfo) -> startPopShow(position, mConversationLayout, conversationInfo));
    }

    @Override
    public void getSystemNoic() {
        model.getSystemNotic("1","1")
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<HomeRoomResult<List<SystemNotic>>>(){

                    @Override
                    protected void success(HomeRoomResult<List<SystemNotic>> bean, BaseResponse<HomeRoomResult<List<SystemNotic>>> response) {
                        List<SystemNotic> records = bean.getRecords();
                        getView().onSystemNoicSuccess(records);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    private void startPopShow(int position, ConversationLayout mConversationLayout, ConversationInfo conversationInfo) {
        TipsDialog tipsDialog = TipsDialog.createDialog(((NewsFragment) getView()).getContext(), R.layout.dialog_news_top_delete);
        if (conversationInfo.isTop()) {
            tipsDialog.setText(R.id.tv_top, "取消置顶");
        } else {
            tipsDialog.setText(R.id.tv_top, "置顶");
        }
        tipsDialog.bindClick(R.id.tv_top, (v, dialog) -> {
            mConversationLayout.setConversationTop(position, conversationInfo);
        });

        tipsDialog.bindClick(R.id.tv_delete, (v, dialog) -> {
            mConversationLayout.deleteConversation(position, conversationInfo);
        });

        tipsDialog.show();

    }

    private void startChatActivity(ConversationInfo conversationInfo) {

        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? V2TIMConversation.V2TIM_GROUP : V2TIMConversation.V2TIM_C2C);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        chatInfo.setIconUrlList(conversationInfo.getIconUrlList());

        Intent intent = new Intent(((NewsFragment) getView()).getContext(), ChatActivity.class);
        intent.putExtra(Constant.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((NewsFragment) getView()).getContext().startActivity(intent);

    }
}
