package com.tencent.qcloud.tim.uikit.modules.conversation.holder;

import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.uikit.R;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationIconView;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.utils.DateTimeUtil;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import java.util.Date;
import java.util.Map;

public class ConversationCommonHolder extends ConversationBaseHolder {

    public ConversationIconView conversationIconView;
    protected LinearLayout leftItemLayout;
    protected TextView titleText;
    protected TextView messageText;
    protected TextView timelineText;
    protected TextView unreadText;
    protected TextView atInfoText;

    public ConversationCommonHolder(View itemView) {
        super(itemView);
        leftItemLayout = rootView.findViewById(R.id.item_left);
        conversationIconView = rootView.findViewById(R.id.conversation_icon);
        titleText = rootView.findViewById(R.id.conversation_title);
        messageText = rootView.findViewById(R.id.conversation_last_msg);
        timelineText = rootView.findViewById(R.id.conversation_time);
        unreadText = rootView.findViewById(R.id.conversation_unread);
        atInfoText = rootView.findViewById(R.id.conversation_at_msg);
    }

    public void layoutViews(ConversationInfo conversation, int position) {
        MessageInfo lastMsg = conversation.getLastMessage();
        if (lastMsg != null && lastMsg.getStatus() == MessageInfo.MSG_STATUS_REVOKE) {
            if (lastMsg.isSelf()) {
                lastMsg.setExtra("您撤回了一条消息");
            } else if (lastMsg.isGroup()) {
                String message = TUIKitConstants.covert2HTMLString(
                        TextUtils.isEmpty(lastMsg.getGroupNameCard())
                                ? lastMsg.getFromUser()
                                : lastMsg.getGroupNameCard());
                lastMsg.setExtra(message + "撤回了一条消息");
            } else {
                lastMsg.setExtra("对方撤回了一条消息");
            }
        }

        if (conversation.isTop()) {
            leftItemLayout.setBackgroundColor(rootView.getResources().getColor(R.color.conversation_top_color));
        } else {
            leftItemLayout.setBackgroundColor(Color.WHITE);
        }

        titleText.setText(conversation.getTitle());
        messageText.setText("");
        timelineText.setText("");
        if (lastMsg != null) {
            if (lastMsg.getTimMessage().getElemType() == V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {

                V2TIMCustomElem elem = lastMsg.getTimMessage().getCustomElem();
                Map<String, String> map = new Gson().fromJson(new String(elem.getData()), Map.class);
                if (map.get("type") != null) {

                    if (map.get("type").equals("Handsel")) {
                        messageText.setText("[装扮]");

                    } else if (map.get("type").equals("Topic")) {
                        messageText.setText("[分享]");
                    } else if (map.get("type").equals("Clan")) {
                        messageText.setText("[分享]");
                    } else if (map.get("type").equals("Live")) {
                        messageText.setText("[分享]");
                    } else if (map.get("type").equals("Gift")) {
                        messageText.setText("[礼物]");
                    } else {
                        messageText.setText("[不支持的自定义消息]");
                    }
                } else {
                    messageText.setText("[不支持的自定义消息]");
                }

                messageText.setTextColor(rootView.getResources().getColor(R.color.list_bottom_text_bg));

            } else {
                if (lastMsg.getExtra() != null) {
                    messageText.setText(Html.fromHtml(lastMsg.getExtra().toString()));
                    messageText.setTextColor(rootView.getResources().getColor(R.color.list_bottom_text_bg));
                }
                timelineText.setText(DateTimeUtil.getTimeFormatText(new Date(lastMsg.getMsgTime() * 1000)));
            }


        }

        if (conversation.getUnRead() > 0) {
            unreadText.setVisibility(View.VISIBLE);
            if (conversation.getUnRead() > 99) {
                unreadText.setText("99+");
            } else {
                unreadText.setText("" + conversation.getUnRead());
            }
        } else {
            unreadText.setVisibility(View.GONE);
        }

        if (conversation.getAtInfoText().isEmpty()) {
            atInfoText.setVisibility(View.GONE);
        } else {
            atInfoText.setVisibility(View.VISIBLE);
            atInfoText.setText(conversation.getAtInfoText());
            atInfoText.setTextColor(Color.RED);
        }

        conversationIconView.setRadius(mAdapter.getItemAvatarRadius());
        if (mAdapter.getItemDateTextSize() != 0) {
            timelineText.setTextSize(mAdapter.getItemDateTextSize());
        }
        if (mAdapter.getItemBottomTextSize() != 0) {
            messageText.setTextSize(mAdapter.getItemBottomTextSize());
        }
        if (mAdapter.getItemTopTextSize() != 0) {
            titleText.setTextSize(mAdapter.getItemTopTextSize());
        }
        if (!mAdapter.hasItemUnreadDot()) {
            unreadText.setVisibility(View.GONE);
        }

        if (conversation.getIconUrlList() != null) {
            conversationIconView.setConversation(conversation);
        }

        //// 由子类设置指定消息类型的views
        layoutVariableViews(conversation, position);
    }

    public void layoutVariableViews(ConversationInfo conversationInfo, int position) {

    }
}
