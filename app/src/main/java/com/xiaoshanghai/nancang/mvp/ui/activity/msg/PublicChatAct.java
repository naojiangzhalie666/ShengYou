package com.xiaoshanghai.nancang.mvp.ui.activity.msg;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.bean.HallMessage;
import com.xiaoshanghai.nancang.callback.OnChatGiftCallback;
import com.xiaoshanghai.nancang.callback.OnChatSeatClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.RoomSeatConstant;
import com.xiaoshanghai.nancang.helper.EnterRoomHelp;
import com.xiaoshanghai.nancang.helper.PublicChatLayoutHelper;
import com.xiaoshanghai.nancang.mvp.contract.PublicChatContract;
import com.xiaoshanghai.nancang.mvp.presenter.PublicChatPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HeadgearAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.NobleWebActivity;
import com.xiaoshanghai.nancang.mvp.ui.view.ChatGiftView;
import com.xiaoshanghai.nancang.mvp.ui.view.ChatSeatView;
import com.xiaoshanghai.nancang.mvp.ui.view.NobleDialog;
import com.xiaoshanghai.nancang.net.bean.ChatGiftSeat;
import com.xiaoshanghai.nancang.net.bean.GiftAllResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.PublicChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.PublicInputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PublicChatAct extends BaseMvpActivity<PublicChatPresenter> implements PublicChatContract.View, OnChatSeatClickCallback, OnChatGiftCallback {

    private static final String TAG = PublicChatAct.class.getName();

    @BindView(R.id.chat_layout)
    public PublicChatLayout mChatLayout;

    private TitleBarLayout mTitleBar;

    private ChatInfo mChatInfo;

    private PublicChatLayoutHelper mHelper;

    private static final int START_SELECT_BDDY = 1;

    private ChatGiftView mChatView;

    private GiftAllResult mChatGift;

    private String mNoble;

    private boolean isChatView = false;

    private boolean isChatSeat = false;

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
            int vis = getWindow().getDecorView().getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            getWindow().getDecorView().setSystemUiVisibility(vis);
            super.onCreate(savedInstanceState);
            mPresenter.attachView(this);
            mPresenter.getGift();
            mPresenter.getSelfNoble();
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_public_chat;
    }

    @Override
    protected PublicChatPresenter createPresenter() {
        return new PublicChatPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        mChatInfo = (ChatInfo) extras.getSerializable(Constant.CHAT_INFO);
    }


    private void initView() {
        mHelper = new PublicChatLayoutHelper(this,this);
        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();
        mHelper.customizeChatLayout(mChatLayout);

        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);

        //获取单聊面板的标题栏
        mTitleBar = mChatLayout.getTitleBar();
        mTitleBar.setBackgroundResource(R.color.white);
        mTitleBar.setLeftIcon(R.mipmap.square_release_back);
        mTitleBar.setRightIcon(0);

        PublicInputLayout inputLayout = mChatLayout.getInputLayout();
        inputLayout.setBackgroundResource(R.color.white);

        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                EventBus.getDefault().post(new HallMessage());
            }
        });

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

                String fromUser = messageInfo.getFromUser();
                mPresenter.getUserInfo(fromUser);
            }
        });

        mChatLayout.getInputLayout().setStartActivityListener(() -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUDDY_KEY, Constant.PUBLIC_CHAT);
            ActStartUtils.startForAct(this, MyBuddyAct.class, bundle, START_SELECT_BDDY);

        });

    }


    private ChatGiftView getChatView(GiftAllResult roomGift, String selfNoble) {
        ChatGiftView chatGiftView = ChatGiftView.Companion.newInstance(roomGift, selfNoble);
        chatGiftView.setOnGiftSendCallback(this);
        return chatGiftView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatLayout != null) {
            mChatLayout.exitChat();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_SELECT_BDDY && resultCode == RESULT_OK) {
            MyFollowResult result = (MyFollowResult) data.getSerializableExtra(Constant.BUDDY_KEY);
            mChatLayout.getInputLayout().updateInputText(result.getUserName(), result.getUserId());
        }
    }

    @Override
    public void onUserInfoSuccess(VoiceRoomSeatEntity.UserInfo userInfo) {
        mPresenter.getFollowStatus(userInfo);
    }

    @Override
    public void onUserFollow(VoiceRoomSeatEntity.UserInfo userInfo, Integer sattus) {
        if (!isChatSeat) {
            isChatSeat = true;
            ChatSeatView chatSeatView = ChatSeatView.Companion.newInstance(userInfo, sattus == 1);
            chatSeatView.setOnSeatClickCallback(this);
            chatSeatView.show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void onGiftSuccess(GiftAllResult roomGift) {
        this.mChatGift = roomGift;

        if (!TextUtils.isEmpty(mNoble) && mChatGift != null) {
            mChatView = getChatView(mChatGift, mNoble);
            mHelper.setChatView(mChatView);
        }
    }

    @Override
    public void onNobleSuccess(int noble) {
        this.mNoble = noble + "";
        if (!TextUtils.isEmpty(mNoble) && mChatGift != null) {
            mChatView = getChatView(mChatGift, mNoble);
            mHelper.setChatView(mChatView);
        }
    }

    @Override
    public void onFollow(String status) {

    }

    @Override
    public void onUnFollow(String status) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSeatItemClick(int status, VoiceRoomSeatEntity.UserInfo userInfo) {
        switch (status) {
            case RoomSeatConstant.SEND_GIFT://送礼物

                if (!isChatView) {
                    isChatView = true;

                    ChatGiftSeat chatGiftSeat = new ChatGiftSeat(userInfo.userPicture, userInfo.userName, userInfo.id, true);
                    mChatView.setGiveAway(chatGiftSeat);

                    FragmentManager supportFragmentManager = getSupportFragmentManager();
                    mChatView.show(supportFragmentManager, TAG);
                }
                break;

            case RoomSeatConstant.PRIVATE_MSG://私信

                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(V2TIMConversation.V2TIM_C2C);
                chatInfo.setId(userInfo.id);
                chatInfo.setChatName(userInfo.userName);
                List<Object> iconUrl = new ArrayList<>();
                iconUrl.add(userInfo.userPicture);
                chatInfo.setIconUrlList(iconUrl);

                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(Constant.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case RoomSeatConstant.SEND_DRESS://送装扮
                Bundle bundle = new Bundle();
                bundle.putString(Constant.CHAT_TYPE, Constant.CHAT_GROUP);
                bundle.putString(Constant.USER_ID, userInfo.id);
                bundle.putString(Constant.PUBLIC_CHAT_GROUP, Constant.PUBLIC_CHAT_GROUP_ID);
                ActStartUtils.startAct(this, HeadgearAct.class, bundle);
                break;
            case RoomSeatConstant.ATTENTION://关注
                mPresenter.follow(userInfo.id, "1");
                break;
            case RoomSeatConstant.UNSUBSCRIBE://取消关注
                mPresenter.follow(userInfo.id, "0");
                break;
            case RoomSeatConstant.STEP_ON://踩他
                EnterRoomHelp.enterRoomControl(this,this,userInfo.roomId);
                break;
            case RoomSeatConstant.WINDOWS_DISMISS://dialog 消失
                isChatSeat = false;
                break;

            case RoomSeatConstant.REPORT_USER://举报
                Bundle jubao = new Bundle();
                jubao.putString(Constant.REPOTR_TYPE, Constant.REPORT_TYPE_USER);
                jubao.putString(Constant.REPORT_ID, userInfo.id);
                ActStartUtils.startAct(this, ReportAct.class, jubao);
                break;
        }
    }

    @Override
    public void onSendGiftResult(Integer status, List<String> users,RoomGiftResult giftResult,Integer giftNum,Boolean isTotal,ChatGiftSeat mGiveAway) {
        if (status == 0) {
            ToastUtils.showShort("余额不足！");
        } else if (status == 1) {
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();
            map.put("type", "Gift");
            map.put("title", giftResult.getGiftName() + "(x" + giftNum + ")");

            String chatName = mGiveAway.getUserName();
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
                ActStartUtils.startAct(this, NobleWebActivity.class);
            });

            mNobleDialog.show(getSupportFragmentManager(), TAG);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        EventBus.getDefault().post(new HallMessage());
        finish();
        return true;
    }
}