package com.xiaoshanghai.nancang.mvp.ui.fragment.msg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.callback.OnChatGiftCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.helper.ChatLayoutHelper;
import com.xiaoshanghai.nancang.mvp.contract.ChatContract;
import com.xiaoshanghai.nancang.mvp.presenter.ChatPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.NobleWebActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ChatBlackListAct;
import com.xiaoshanghai.nancang.mvp.ui.view.ChatGiftView;
import com.xiaoshanghai.nancang.mvp.ui.view.NobleDialog;
import com.xiaoshanghai.nancang.net.bean.ChatGiftSeat;
import com.xiaoshanghai.nancang.net.bean.GiftAllResult;
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class ChatFragment extends BaseMvpFragment<ChatPresenter> implements ChatContract.View, OnChatGiftCallback {

    @BindView(R.id.chat_layout)
    public ChatLayout mChatLayout;

    private TitleBarLayout mTitleBar;

    private ChatInfo mChatInfo;

    private GiftAllResult mChatGift;

    private String mNoble;

    private ChatGiftView mChatView;

    private ChatLayoutHelper mHelper;

    public static boolean isChatView = false;


    @Override
    public int setLayoutId() {
        return R.layout.chat_fragment;
    }

    private void initView() {

        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();

        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);

        //获取单聊面板的标题栏
        mTitleBar = mChatLayout.getTitleBar();
        mTitleBar.setBackgroundResource(R.color.white);
        mTitleBar.setLeftIcon(R.mipmap.square_release_back);
        mTitleBar.setRightIcon(R.mipmap.icon_chat_settings);

        InputLayout inputLayout = mChatLayout.getInputLayout();
        inputLayout.setBackgroundResource(R.color.white);

        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (mChatInfo.getType() == V2TIMConversation.V2TIM_C2C) {
            mTitleBar.setOnRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.CHAT_INFO, mChatInfo);
                    ActStartUtils.startAct(getActivity(), ChatBlackListAct.class, bundle);
                }
            });
        }

        mChatLayout.getMessageLayout().setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constant.USER_ID, messageInfo.getFromUser());
                ActStartUtils.startAct(getActivity(), HomePageAct.class, bundle);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        mChatInfo = (ChatInfo) bundle.getSerializable(Constant.CHAT_INFO);
        if (mChatInfo == null) {
            return;
        }
        initView();

        // TODO 通过api设置ChatLayout各种属性的样例
        mHelper.customizeChatLayout(mChatLayout);

    }

    @Override
    public void onPause() {
        super.onPause();
        AudioPlayer.getInstance().stopPlay();
    }

    @Override
    protected ChatPresenter createPresenter() {
        return new ChatPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatLayout != null) {
            mChatLayout.exitChat();
        }
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mHelper = new ChatLayoutHelper(this,getActivity());
        mPresenter.getGift();
        mPresenter.getSelfNoble();
    }

    @Override
    public void onError(@Nullable String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onGiftSuccess(@Nullable GiftAllResult roomGift) {
        this.mChatGift = roomGift;

        if (!TextUtils.isEmpty(mNoble) && mChatGift != null) {
            mChatView = getChatView(mChatGift, mNoble);
            mHelper.setChatView(mChatView);
        }
    }

    @Override
    public void onNobleSuccess(@Nullable Integer noble) {
        this.mNoble = noble.toString();
        if (!TextUtils.isEmpty(mNoble) && mChatGift != null) {
            mChatView = getChatView(mChatGift, mNoble);
            mHelper.setChatView(mChatView);
        }
    }

    private ChatGiftView getChatView(GiftAllResult roomGift, String selfNoble) {
        ChatGiftView chatGiftView = ChatGiftView.Companion.newInstance(roomGift, selfNoble);
        ChatGiftSeat chatGiftSeat = new ChatGiftSeat(mChatInfo.getIconUrlList().get(0).toString(), mChatInfo.getChatName(), mChatInfo.getId(), true);
        chatGiftView.setGiveAway(chatGiftSeat);
        chatGiftView.setOnGiftSendCallback(this);
        return chatGiftView;
    }

    @Override
    public void onSendGiftResult(Integer status,List<String> users,RoomGiftResult giftResult,  Integer giftNum, Boolean isTotal,ChatGiftSeat mGiveAway) {
        if (status == 0) {
            ToastUtils.showShort("余额不足！");
        } else if (status == 1) {
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();
            map.put("type", "Gift");
            map.put("title", giftResult.getGiftName() + "(x" + giftNum + ")");
            String chatName = mChatInfo.getChatName();
            String name;
            if (chatName.length() >= 5) {
                name = chatName.substring(0, 5) + "...";
            } else {
                name = chatName;
            }
            map.put("detail", "赠给" + name);
            map.put("img", giftResult.getGiftStaticUrl());
            String data = gson.toJson(map);
            MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
            mChatLayout.sendMessage(info, false);

        } else if (status == 2) {

            mChatView.dismiss();

            NobleDialog mNobleDialog = NobleDialog.Companion.newInstance(mNoble, giftResult.getNobleId());

            mNobleDialog.setOnClick(view -> {
                ActStartUtils.startAct(getActivity(), NobleWebActivity.class);
            });

            mNobleDialog.show(getFragmentManager(), "ChatFragment");
        } else {
            ToastUtils.showShort("礼物赠送失败");
        }
    }

    @Override
    public void onNobleClick() {

    }

    @Override
    public void onGiftDismiss() {
        isChatView = false;
    }
}
