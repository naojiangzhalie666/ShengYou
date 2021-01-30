package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;

import java.util.Map;

public class CustomRoomController {

    private static final String TAG = CustomRoomController.class.getSimpleName();


    public static void onDraw(BaseView baseView,Context context, ICustomMessageViewGroup parent, final Map<String, String> data, boolean self) {

        // 把自定义消息view添加到TUIKit内部的父容器里
        View view = LayoutInflater.from(context).inflate(R.layout.custom_message_room_layout, null, false);
        parent.addMessageContentView(view);

        TextView mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(data.get("title"));

        TextView mTvJoin = view.findViewById(R.id.tv_join);

        CircleImageView mCivAvatar = view.findViewById(R.id.civ_avatar);
        GlideAppUtil.loadImage(context,data.get("img"),mCivAvatar);


        if (self) {
            mTvTitle.setTextColor(ContextCompat.getColor(context,R.color.white));
            mTvJoin.setTextColor(ContextCompat.getColor(context,R.color.white));
        } else {
            mTvTitle.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
            mTvJoin.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
        }

        view.setFocusable(true);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterRoomHelp.enterRoomControl(context,baseView,data.get("id"));
            }
        });

    }
}
