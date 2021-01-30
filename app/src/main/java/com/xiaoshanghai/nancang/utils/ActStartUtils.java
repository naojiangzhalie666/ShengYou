package com.xiaoshanghai.nancang.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.LoginStatus;
import com.xiaoshanghai.nancang.constant.URLConstant;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.RegisterActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.MainActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.WebActivity;
import com.xiaoshanghai.nancang.net.bean.LogonResult;


public class ActStartUtils {

    public ActStartUtils() {
        throw new UnsupportedOperationException("ActivitySkipUtil不能实例化");
    }

    /**
     * 功能描述:简单地Activity的跳转(不携带任何数据)
     *
     * @param context 发起跳转的Activity实例
     * @param cls     目标Activity实例
     */
    public static void startAct(Context context, Class<? extends Activity> cls) {
        if (cls == null) return;
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 功能描述：带数据的Activity之间的跳转
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public static void startAct(Context context, Class<? extends Activity> cls, Bundle bundle) {
        if (cls == null) return;
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 带参数携带结果回调
     *
     * @param context
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public static void startForAct(Activity context, Class<? extends Activity> cls, Bundle bundle, int requestCode) {
        if (cls == null) return;
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 不带参数携带结果回调
     *
     * @param context
     * @param cls
     * @param requestCode
     */
    public static void startForAct(Activity context, Class<? extends Activity> cls, int requestCode) {
        if (cls == null) return;
        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
    }

    public static void loginStartAct(Activity activity, LogonResult result, Bundle bundle) {
        if (result == null) return;
        switch (LoginStatus.getValue(result.getStatus())) {
            //状态，1成功 2账号冻结 3密码错误 4用户不存在 5验证码错误 6手机号为空 7密码为空 8验证码为空 9验证码超时
            case SUCCESS:   //成功
                if (bundle == null) {
                    startAct(activity, MainActivity.class);
                } else {
                    startAct(activity, MainActivity.class, bundle);
                }
                break;
            case FREEZE:    //冻结
                ToastUtils.showShort(activity.getResources().getString(R.string.freeze));
                break;
            case PSDWRONG:  //密码错误
                ToastUtils.showShort(activity.getResources().getString(R.string.psd_error));
                break;
            case NOTEXIST:  //用户不存在
                if (bundle == null) {
                    ToastUtils.showShort(activity.getResources().getString(R.string.user_not));
                } else {
                    startAct(activity, RegisterActivity.class, bundle);
                }
                break;
            case CODEERROR:  //验证码错误
                ToastUtils.showShort(activity.getResources().getString(R.string.code_error));
                break;
            case OUTTIME:       //验证码超时
                ToastUtils.showShort(activity.getResources().getString(R.string.code_out_time));
                break;

        }

    }

    public static void webActStart(Activity activity,String type) {
        switch (type) {
            case Constant.PRIVACY:  //隐私政策
                Bundle bundle = new Bundle();
                bundle.putString(Constant.WEB_URL, URLConstant.PRIVACY_URL);
                bundle.putString(Constant.WEB_TITLE, "隐私政策");
                startAct(activity, WebActivity.class, bundle);
                break;
            case Constant.USER_AGREEMENT: //用户协议
                Bundle userBundel = new Bundle();
                userBundel.putString(Constant.WEB_URL, URLConstant.USER_AGREEMENT_URL);
                userBundel.putString(Constant.WEB_TITLE, "用户协议");
                startAct(activity, WebActivity.class, userBundel);
                break;
        }

    }

}
