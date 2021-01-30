package com.xiaoshanghai.nancang.mvp.ui.activity.msg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.ChatFragment;
import com.xiaoshanghai.nancang.thirdpush.OfflineMessageDispatcher;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.base.OfflineMessageBean;

import static com.tencent.imsdk.v2.V2TIMManager.V2TIM_STATUS_LOGINED;

public class ChatActivity extends BaseMvpActivity {

    private static final String TAG = ChatActivity.class.getSimpleName();

    private ChatFragment mChatFragment;

    private ChatInfo mChatInfo;

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
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        chat(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void chat(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            startSplashActivity(null);
            return;
        }

        OfflineMessageBean bean = OfflineMessageDispatcher.parseOfflineMessage(intent);
        if (bean != null) {
            mChatInfo = new ChatInfo();
            mChatInfo.setType(bean.chatType);
            mChatInfo.setId(bean.sender);
            bundle.putSerializable(Constant.CHAT_INFO, mChatInfo);
        } else {
            mChatInfo = (ChatInfo) bundle.getSerializable(Constant.CHAT_INFO);
            if (mChatInfo == null) {
                startSplashActivity(null);
                return;
            }
        }

        if (V2TIMManager.getInstance().getLoginStatus() == V2TIM_STATUS_LOGINED) {
            mChatFragment = new ChatFragment();
            mChatFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.empty_view, mChatFragment).commitAllowingStateLoss();
        } else {
            startSplashActivity(bundle);
        }
    }

    private void startSplashActivity(Bundle bundle) {
//        Intent intent = new Intent(ChatActivity.this, SplashActivity.class);
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        startActivity(intent);
//        finish();
    }

    @Override
    public int setLayoutId() {
        return R.layout.chat_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        chat(getIntent());
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
