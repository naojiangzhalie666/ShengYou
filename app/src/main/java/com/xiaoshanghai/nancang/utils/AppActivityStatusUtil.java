package com.xiaoshanghai.nancang.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.LoginActivity;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;

import java.util.ArrayList;

/**
 * 文件名:AppActivityStatusUtil
 * 创建者:zed
 * 创建日期:2019/8/20 10:52
 * 描述:TODO
 */
public class AppActivityStatusUtil {
    private static ArrayList<Activity> ACTIVITY_PROXY = new ArrayList<>();

    public static void register(Activity activity) {
        if (ACTIVITY_PROXY != null)
            ACTIVITY_PROXY.add(activity);
    }

    public static void unRegister(Activity activity) {
        if (ACTIVITY_PROXY != null && ACTIVITY_PROXY.contains(activity))
            ACTIVITY_PROXY.remove(activity);
    }

    public static void backToLogin() {
        final Activity currentActivity = getTopActivity();
        if (currentActivity == null) return;

        TipsDialog.createDialog(currentActivity, R.layout.login_expired)
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .bindClick(R.id.tv_cancel, (v, dialog) -> {

                    String loginUser = V2TIMManager.getInstance().getLoginUser();

                    if (TextUtils.isEmpty(loginUser)) {
                        ActStartUtils.startAct(currentActivity, LoginActivity.class);
                        SPUtils.getInstance().clear();
                        clearAllActivity();
                    } else {
                        ActStartUtils.startAct(currentActivity, LoginActivity.class);
                        SPUtils.getInstance().clear();
                        clearAllActivity();
                        TRTCVoiceRoom.sharedInstance(currentActivity).logout(null);
                    }
                }).show();
    }

    public static void logout() {
        finishActivitys();
    }

    /**
     * 找到最顶层activity 并且当前activity并未 finish
     *
     * @return
     */
    private static Activity getTopActivity() {
        try {
            if (ACTIVITY_PROXY.size() == 0) return null;
            for (int i = ACTIVITY_PROXY.size() - 1; i >= 0; i--) {
                if (!ACTIVITY_PROXY.get(i).isFinishing())
                    return ACTIVITY_PROXY.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void clearAllActivity() {
        for (final Activity activity : ACTIVITY_PROXY) {
            if (activity != null && !activity.getClass().getSimpleName().equals(LoginActivity.class.getSimpleName())) {
                activity.finish();
//                ACTIVITY_PROXY.remove(activity);
            }
        }
    }

    /**
     * 关闭所有的activity，退出应用
     */
    private static void finishActivitys() {
        for (final Activity activity : ACTIVITY_PROXY) {
            if (activity != null && !activity.getClass().getSimpleName().equals(LoginActivity.class.getSimpleName())) {
                activity.finish();
            }
        }
    }

}
