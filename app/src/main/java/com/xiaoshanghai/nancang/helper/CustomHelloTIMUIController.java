package com.xiaoshanghai.nancang.helper;

import android.view.LayoutInflater;
import android.view.View;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseApplication;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.holder.ICustomMessageViewGroup;

import java.util.Map;

public class CustomHelloTIMUIController {

    private static final String TAG = CustomHelloTIMUIController.class.getSimpleName();

    public static void onDraw(ICustomMessageViewGroup parent, final Map data) {

        // 把自定义消息view添加到TUIKit内部的父容器里
        View view = LayoutInflater.from(BaseApplication.getApplication()).inflate(R.layout.test_custom_message_layout1, null, false);
        parent.addMessageContentView(view);
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
//                BaseApplication.getApplication().startActivity(intent);
//            }
//        });
    }
}
