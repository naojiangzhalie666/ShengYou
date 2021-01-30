package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.MessageDetailsAct;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;

import java.util.Map;

public class CustomTopiclController {

    private static final String TAG = CustomTopiclController.class.getSimpleName();

    public static void onDraw(Context context, ICustomMessageViewGroup parent, final Map<String, String> data, boolean self) {

        // 把自定义消息view添加到TUIKit内部的父容器里
        View view = LayoutInflater.from(context).inflate(R.layout.custom_message_handsel_layout, null, false);
        parent.addMessageContentView(view);

        CircleImageView mCivAvatar = view.findViewById(R.id.civ_avatar);

        String img = data.get("img");
        if (img.startsWith("http")) {
        GlideAppUtil.loadImage(context,data.get("img"),mCivAvatar);

        } else {
        GlideAppUtil.loadImage(context,R.mipmap.icon_chat_share,mCivAvatar);

        }


        TextView mTvGiftName = view.findViewById(R.id.tv_gift_name);
        mTvGiftName.setText(data.get("title"));

        TextView mTvGiftUser = view.findViewById(R.id.tv_gift_user);
        mTvGiftUser.setText(data.get("detail"));

        if (self) {
            mTvGiftName.setTextColor(ContextCompat.getColor(context,R.color.white));
            mTvGiftUser.setTextColor(ContextCompat.getColor(context,R.color.white));
        } else {
            mTvGiftName.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
            mTvGiftUser.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
        }


        view.setClickable(true);
        view.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.TOPIC_ID,data.get("id"));
            ActStartUtils.startAct(context, MessageDetailsAct.class, bundle);
        });

    }
}
