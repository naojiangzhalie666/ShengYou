package com.xiaoshanghai.nancang.service;

import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class MessageReceiver extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onSetAccountResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteAccountResult(Context context, int i, String s) {

    }

    @Override
    public void onSetAttributeResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteAttributeResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        Log.e("aa","--------------------"+xgPushTextMessage.getTitle()+"---"+xgPushTextMessage.getContent());
    }

    @Override
    public void onNotificationClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotificationShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
