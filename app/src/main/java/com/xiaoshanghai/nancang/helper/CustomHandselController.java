package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;

import java.util.Map;

public class CustomHandselController {

    private static final String TAG = CustomHandselController.class.getSimpleName();

    public static void onDraw(Context context, ICustomMessageViewGroup parent, final Map<String, String> data, boolean self) {

        // 把自定义消息view添加到TUIKit内部的父容器里
        View view = LayoutInflater.from(context).inflate(R.layout.custom_message_handsel_layout, null, false);
        parent.addMessageContentView(view);

        CircleImageView mCivAvatar = view.findViewById(R.id.civ_avatar);
        GlideAppUtil.loadImage(context,data.get("img"),mCivAvatar);

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




//
//        // 自定义消息view的实现，这里仅仅展示文本信息，并且实现超链接跳转
//        TextView textView = view.findViewById(R.id.test_custom_message_tv);
//        final String text = "不支持的自定义消息";
//        if (data == null) {
//            textView.setText(text);
//        } else {
//            textView.setText(data.text);
//        }
//        view.setClickable(true);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data == null) {
////                    DemoLog.e(TAG, "Do what?");
//                    ToastUtil.toastShortMessage(text);
//                    return;
//                }
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(data.link);
//                intent.setData(content_url);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }
}
