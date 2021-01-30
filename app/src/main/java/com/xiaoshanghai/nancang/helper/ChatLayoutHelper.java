package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseActivity;
import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HeadgearAct;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.ChatFragment;
import com.xiaoshanghai.nancang.mvp.ui.view.ChatGiftView;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.BaseInputFragment;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.inputmore.InputMoreActionUnit;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.IOnCustomMessageDrawListener;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.Map;

/**
 * IM 使用帮助 请勿删注释代码
 */
public class ChatLayoutHelper {

    private static final String TAG = ChatLayoutHelper.class.getSimpleName();

    private Context mContext;

    private BaseView mBaseView;

    private ChatGiftView mChatView;

    public ChatLayoutHelper(BaseView baseView,Context context) {
        this.mBaseView = baseView;
        this.mContext = context;
    }

    public void setChatView(ChatGiftView chatView) {
        mChatView = chatView;
    }

    public void customizeChatLayout(final ChatLayout layout) {

//        //====== NoticeLayout使用范例 ======//
//        NoticeLayout noticeLayout = layout.getNoticeLayout();
//        noticeLayout.alwaysShow(true);
//        noticeLayout.getContent().setText("现在插播一条广告");
//        noticeLayout.getContentExtra().setText("参看有奖");
//        noticeLayout.setOnNoticeClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.toastShortMessage("赏白银五千两");
//            }
//        });
//
        //====== MessageLayout使用范例 ======//
        MessageLayout messageLayout = layout.getMessageLayout();
//        ////// 设置聊天背景 //////
        messageLayout.setBackground(new ColorDrawable(0xFFF5F5F5));
//        ////// 设置头像 //////
//        // 设置默认头像，默认与朋友与自己的头像相同
        messageLayout.setAvatar(R.mipmap.icon_default_avatar);
//        // 设置头像圆角
        messageLayout.setAvatarRadius(100);
//        // 设置头像大小
        messageLayout.setAvatarSize(new int[]{54, 54});
//
//        ////// 设置昵称样式（对方与自己的样式保持一致）//////
//        messageLayout.setNameFontSize(12);
//        messageLayout.setNameFontColor(0xFF8B5A2B);
//
//        ////// 设置气泡 ///////
//        // 设置自己聊天气泡的背景
//        messageLayout.setRightBubble(R.drawable.img_im_bg);
        messageLayout.setRightBubble(ContextCompat.getDrawable(mContext, R.drawable.img_im_bg));
//        // 设置朋友聊天气泡的背景
        messageLayout.setLeftBubble(ContextCompat.getDrawable(mContext, R.drawable.img_im_white_bg));
//
//        ////// 设置聊天内容 //////
//        // 设置聊天内容字体字体大小，朋友和自己用一种字体大小
        messageLayout.setChatContextFontSize(16);
//        // 设置自己聊天内容字体颜色
        messageLayout.setRightChatContentFontColor(0xFFFFFFFF);
//        // 设置朋友聊天内容字体颜色
        messageLayout.setLeftChatContentFontColor(0xFF000000);
//
//        ////// 设置聊天时间 //////
//        // 设置聊天时间线的背景
//        messageLayout.setChatTimeBubble(ContextCompat.getDrawable(mContext, R.drawable.shape_time_line));
//        // 设置聊天时间的字体大小
        messageLayout.setChatTimeFontSize(12);
//        // 设置聊天时间的字体颜色
//        messageLayout.setChatTimeFontColor(0xFFFFFFFF);
//
//        ////// 设置聊天的提示信息 //////
//        // 设置提示的背景
//        messageLayout.setTipsMessageBubble(new ColorDrawable(0xFFE4E7EB));
//        // 设置提示的字体大小
//        messageLayout.setTipsMessageFontSize(12);
//        // 设置提示的字体颜色
//        messageLayout.setTipsMessageFontColor(0xFF7E848C);
//
        // 设置自定义的消息渲染时的回调
        messageLayout.setOnCustomMessageDrawListener(new CustomMessageDraw(mBaseView,mContext));
//
//        // 新增一个PopMenuAction
//        PopMenuAction action = new PopMenuAction();
//        action.setActionName("test");
//        action.setActionClickListener(new PopActionClickListener() {
//            @Override
//            public void onActionClick(int position, Object data) {
//                ToastUtil.toastShortMessage("新增一个pop action");
//            }
//        });
//        messageLayout.addPopAction(action);
//
//        final MessageLayout.OnItemClickListener l = messageLayout.getOnItemClickListener();
//        messageLayout.setOnItemClickListener(new MessageLayout.OnItemClickListener() {
//            @Override
//            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
//                l.onMessageLongClick(view, position, messageInfo);
//                ToastUtil.toastShortMessage("demo中自定义长按item");
//            }
//
//            @Override
//            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
//                l.onUserIconClick(view, position, messageInfo);
//                ToastUtil.toastShortMessage("demo中自定义点击头像");
//            }
//        });


        //====== InputLayout使用范例 ======//
        InputLayout inputLayout = layout.getInputLayout();

//        // TODO 隐藏音频输入的入口，可以打开下面代码测试
//        inputLayout.disableAudioInput(true);
//        // TODO 隐藏表情输入的入口，可以打开下面代码测试
//        inputLayout.disableEmojiInput(true);
//        // TODO 隐藏更多功能的入口，可以打开下面代码测试
//        inputLayout.disableMoreInput(true);
//        // TODO 可以用自定义的事件来替换更多功能的入口，可以打开下面代码测试
//        inputLayout.replaceMoreInput(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.toastShortMessage("自定义的更多功能按钮事件");
//                MessageInfo info = MessageInfoUtil.buildTextMessage("自定义的消息");
//                layout.sendMessage(info, false);
//            }
//        });
//        // TODO 可以用自定义的fragment来替换更多功能，可以打开下面代码测试
//        inputLayout.replaceMoreInput(new CustomInputFragment().setChatLayout(layout));
//
//        // TODO 可以disable更多面板上的各个功能，可以打开下面代码测试
        inputLayout.disableCaptureAction(true);
        inputLayout.disableSendFileAction(true);
//        inputLayout.disableSendPhotoAction(true);
        inputLayout.disableVideoRecordAction(true);
//        inputLayout.enableAudioCall();
//        inputLayout.enableVideoCall();


        // TODO 可以自己增加一些功能，可以打开下面代码测试
        // 增加一个欢迎提示富文本
//        InputMoreActionUnit unit = new InputMoreActionUnit();
//        unit.setIconResId(R.drawable.custom);
//        unit.setTitleId(R.string.test_custom_action);
//        unit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Gson gson = new Gson();
//                CustomHelloMessage customHelloMessage = new CustomHelloMessage();
//                customHelloMessage.version = TUIKitConstants.version;
//                customHelloMessage.text = "欢迎加入云通信IM大家庭！";
//                customHelloMessage.link = "https://cloud.tencent.com/document/product/269/3794";
//                customHelloMessage.img = "http://192.168.0.12:8080/imgs/portrait/9ee762b5-862d-4b86-a8fd-a42a7b5fb975";
//                String data = gson.toJson(customHelloMessage);
//                MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
//                layout.sendMessage(info, false);
//                ChatInfo currentChatInfo = layout.getChatManager().getCurrentChatInfo();
//
////                V2TIMManager.getInstance().sendC2CCustomMessage(data.getBytes(),currentChatInfo.getId() , new V2TIMValueCallback<V2TIMMessage>() {
////                    @Override
////                    public void onError(int i, String s) {
////
////                    }
////
////                    @Override
////                    public void onSuccess(V2TIMMessage v2TIMMessage) {
////
////
////                    }
////                });
//
//            }
//        });


        InputMoreActionUnit dress = new InputMoreActionUnit();
        dress.setIconResId(R.mipmap.icon_chat_attire);
        dress.setTitleId(R.string.chat_dress);
        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.CHAT_TYPE, Constant.CHAT_USER);
                bundle.putString(Constant.USER_ID, layout.getChatManager().getCurrentChatInfo().getId());
                ActStartUtils.startAct(mContext, HeadgearAct.class, bundle);


//                Gson gson = new Gson();
//                CustomHelloMessage customHelloMessage = new CustomHelloMessage();
//                customHelloMessage.version = TUIKitConstants.version;
//                customHelloMessage.text = "这里测试自定义消息！";
//                customHelloMessage.link = "https://cloud.tencent.com/document/product/269/3794";
//                customHelloMessage.img = "http://192.168.0.12:8080/imgs/portrait/9ee762b5-862d-4b86-a8fd-a42a7b5fb975";
//                String data = gson.toJson(customHelloMessage);
//                MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
//
//                V2TIMMessage v2TIMMessage = info.getTimMessage();
//
//                OfflineMessageContainerBean containerBean = new OfflineMessageContainerBean();
//                OfflineMessageBean entity = new OfflineMessageBean();
//                entity.content = info.getExtra().toString();
//                entity.sender = info.getFromUser();
//                entity.nickname = TUIKitConfigs.getConfigs().getGeneralConfig().getUserNickname();
//                entity.faceUrl = TUIKitConfigs.getConfigs().getGeneralConfig().getUserFaceUrl();
//                containerBean.entity = entity;
//
//                V2TIMOfflinePushInfo v2TIMOfflinePushInfo = new V2TIMOfflinePushInfo();
//                v2TIMOfflinePushInfo.setExt(new Gson().toJson(containerBean).getBytes());
//                // OPPO必须设置ChannelID才可以收到推送消息，这个channelID需要和控制台一致
//                v2TIMOfflinePushInfo.setAndroidOPPOChannelID("tuikit");
//
//                V2TIMManager instance = V2TIMManager.getInstance();
//                V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, layout.getChatManager().getCurrentChatInfo().getId(), null,
//                        V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, v2TIMOfflinePushInfo, new V2TIMSendCallback<V2TIMMessage>() {
//                            @Override
//                            public void onProgress(int i) {
//
//                            }
//
//                            @Override
//                            public void onError(int i, String s) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(V2TIMMessage v2TIMMessage) {
//
//                            }
//                        }
//                );

            }
        });

        InputMoreActionUnit gift = new InputMoreActionUnit();
        gift.setIconResId(R.mipmap.icon_chat_gift);
        gift.setTitleId(R.string.gift);
        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("礼物");
                layout.getInputLayout().hideSoftInput();

                if (!ChatFragment.isChatView) {
                    ChatFragment.isChatView = true;
                    FragmentManager supportFragmentManager = ((BaseActivity) mContext).getSupportFragmentManager();
                    mChatView.show(supportFragmentManager,"ChatFragment");
                }

//                ActStartUtils.startAct(mContext, ChatGiftAct.class);

//                PopupWindow pop = new PopupWindow(mContext);
//                pop.setContentView(new CashHeader(mContext));
//                pop.showAsDropDown(layout, Gravity.BOTTOM, 0, 0);


            }
        });


        inputLayout.addAction(dress);
        inputLayout.addAction(gift);


    }

    public static class CustomInputFragment extends BaseInputFragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View baseView = inflater.inflate(R.layout.test_chat_input_custom_fragment, container, false);
            Button btn1 = baseView.findViewById(R.id.test_send_message_btn1);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.toastShortMessage("自定义的按钮1");
                    if (getChatLayout() != null) {
                        Gson gson = new Gson();
                        CustomHelloMessage customHelloMessage = new CustomHelloMessage();
                        String data = gson.toJson(customHelloMessage);
                        MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
                        getChatLayout().sendMessage(info, false);
                    }
                }
            });
            Button btn2 = baseView.findViewById(R.id.test_send_message_btn2);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.toastShortMessage("自定义的按钮2");
                    if (getChatLayout() != null) {
                        Gson gson = new Gson();
                        CustomHelloMessage customHelloMessage = new CustomHelloMessage();
                        String data = gson.toJson(customHelloMessage);
                        MessageInfo info = MessageInfoUtil.buildCustomMessage(data);
                        getChatLayout().sendMessage(info, false);
                    }
                }
            });
            return baseView;
        }

    }

    public static class CustomMessageDraw implements IOnCustomMessageDrawListener {
        private BaseView mBaseView;
        private Context mContext;
        public CustomMessageDraw(BaseView baseView,Context context) {
            this.mBaseView = baseView;
            this.mContext = context;
        }

        /**
         * 自定义消息渲染时，会调用该方法，本方法实现了自定义消息的创建，以及交互逻辑
         *
         * @param parent 自定义消息显示的父View，需要把创建的自定义消息view添加到parent里
         * @param info   消息的具体信息
         */
        @Override
        public void onDraw(ICustomMessageViewGroup parent, MessageInfo info) {
            // 获取到自定义消息的json数据
            if (info.getTimMessage().getElemType() != V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
                return;
            }


            V2TIMCustomElem elem = info.getTimMessage().getCustomElem();

            boolean self = info.getTimMessage().isSelf();


            //===================================================================

            // 自定义的json数据，需要解析成bean实例
//            CustomHelloMessage data = null;
//            try {
//
//                Map map = new Gson().fromJson(new String(elem.getData()), Map.class);
//
//
//                data = new Gson().fromJson(new String(elem.getData()), CustomHelloMessage.class);
//            } catch (Exception e) {
////                DemoLog.w(TAG, "invalid json: " + new String(elem.getData()) + " " + e.getMessage());
//            }
//            if (data == null) {
////                DemoLog.e(TAG, "No Custom Data: " + new String(elem.getData()));
//            } else if (data.version == TUIKitConstants.JSON_VERSION_1
//                    || (data.version == TUIKitConstants.JSON_VERSION_4 && data.businessID.equals("text_link"))) {
//                CustomHelloTIMUIController.onDraw(parent, data);
//
//
//            } else {
////                DemoLog.w(TAG, "unsupported version: " + data);
//            }
            //===================================================================

            Map<String, String> map = new Gson().fromJson(new String(elem.getData()), Map.class);

            if (map.get("type") != null) {

                if (map.get("type").equals("Handsel")) {    //礼物装扮
                    CustomHandselController.onDraw(mContext, parent, map, self);
                } else if (map.get("type").equals("Topic")) {   //话题
                    CustomTopiclController.onDraw(mContext, parent, map, self);
                } else if (map.get("type").equals("Clan")) {    //家族
                    CustomClanController.onDraw(mContext, parent, map, self);
                } else if (map.get("type").equals("Live")) {    //直播
                    CustomRoomController.onDraw(mBaseView,mContext, parent, map, self);
                } else if (map.get("type").equals("Gift")) {    //礼物
                    CustomHandselController.onDraw(mContext, parent, map, self);
                }
            }

        }
    }

}
