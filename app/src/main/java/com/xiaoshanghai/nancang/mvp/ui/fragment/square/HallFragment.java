package com.xiaoshanghai.nancang.mvp.ui.fragment.square;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.URLConstant;
import com.xiaoshanghai.nancang.helper.ChatLayoutHelper;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.WebActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.PublicChatAct;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatProvider;
import com.tencent.qcloud.tim.uikit.modules.chat.interfaces.IChatProvider;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageListAdapter;
import com.tencent.qcloud.tim.uikit.modules.group.info.GroupInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class HallFragment extends BaseMvpFragment implements GroupChatManagerKit.GroupNotifyHandler {

    @BindView(R.id.chat_message_layout)
    MessageLayout mChatMessage;


    private GroupChatManagerKit mGroupChatManager;

    protected MessageListAdapter mAdapter;

    @Override

    public int setLayoutId() {
        return R.layout.fragment_hall;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mChatMessage.setLayoutManager(layoutManager);

        if (mChatMessage.getAdapter() == null) {
            mAdapter = new MessageListAdapter();
            mChatMessage.setAdapter(mAdapter);
        }

        //        // 设置默认头像，默认与朋友与自己的头像相同
        mChatMessage.setAvatar(R.mipmap.icon_default_avatar);
//        // 设置头像圆角
        mChatMessage.setAvatarRadius(100);
//        // 设置头像大小
        mChatMessage.setAvatarSize(new int[]{54, 54});

        mChatMessage.setRightBubble(ContextCompat.getDrawable(getActivity(), R.drawable.img_im_bg));
//        // 设置朋友聊天气泡的背景
        mChatMessage.setLeftBubble(ContextCompat.getDrawable(getActivity(), R.drawable.img_im_white_bg));
        // 设置聊天内容字体字体大小，朋友和自己用一种字体大小
        mChatMessage.setChatContextFontSize(16);
//        // 设置自己聊天内容字体颜色
        mChatMessage.setRightChatContentFontColor(0xFFFFFFFF);
//        // 设置朋友聊天内容字体颜色
        mChatMessage.setLeftChatContentFontColor(0xFF000000);
        mChatMessage.setOnCustomMessageDrawListener(new ChatLayoutHelper.CustomMessageDraw(this, getActivity()));

        mGroupChatManager = GroupChatManagerKit.getInstance();
        mGroupChatManager.setGroupHandler(this);
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(Constant.PUBLIC_CHAT_GROUP_ID);
        groupInfo.setChatName("公聊大厅");
        mGroupChatManager.setCurrentChatInfo(groupInfo);
        loadChatMessages(null);

    }

    @Override
    public void onResume() {
        super.onResume();
//        loadChatMessages(null);
    }

    public void loadChatMessages(final MessageInfo lastMessage) {
        mGroupChatManager.loadChatMessages(lastMessage, new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (lastMessage == null && data != null) {
                    setDataProvider((ChatProvider) data);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtil.toastLongMessage(errMsg);
                if (lastMessage == null) {
                    setDataProvider(null);
                }
            }
        });
    }

    public void setDataProvider(IChatProvider provider) {

        if (mAdapter != null) {
            mAdapter.setDataSource(provider);
            mGroupChatManager.setLastMessageInfo(mAdapter.getItemCount() > 0 ? mAdapter.getItem(1) : null);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onGroupForceExit() {
//        ChatInfo chatInfo = new ChatInfo();
//        chatInfo.setType(conversationInfo.isGroup() ? V2TIMConversation.V2TIM_GROUP : V2TIMConversation.V2TIM_C2C);
//        chatInfo.setId(conversationInfo.getId());
//        chatInfo.setChatName(conversationInfo.getTitle());
//        chatInfo.setIconUrlList(conversationInfo.getIconUrlList());
    }

    @Override
    public void onGroupNameChanged(String newName) {

    }

    @Override
    public void onApplied(int size) {

    }

    @OnClick({R.id.iv_rank, R.id.card_public_cat, R.id.click_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rank:
                Bundle rank = new Bundle();

                rank.putString(Constant.WEB_URL, URLConstant.RANK_URL);
//                rank.putString(Constant.WEB_URL, "file:///android_asset/rank/index.html");
                rank.putString(Constant.WEB_TITLE, "排行榜");
                ActStartUtils.startAct(getActivity(), WebActivity.class, rank);
                break;
            case R.id.card_public_cat:
            case R.id.click_chat:
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(V2TIMConversation.V2TIM_GROUP);
                chatInfo.setId(Constant.PUBLIC_CHAT_GROUP_ID);
                chatInfo.setChatName("公聊大厅");

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CHAT_INFO, chatInfo);
                ActStartUtils.startAct(getActivity(), PublicChatAct.class, bundle);
                break;

        }
    }

}
