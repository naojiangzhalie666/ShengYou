package com.xiaoshanghai.nancang.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseApplication;

import java.lang.ref.WeakReference;

public class CountDownTimerUtils extends CountDownTimer {

    WeakReference<TextView> mTextView; //显示倒计时的文字  用弱引用 防止内存泄漏


    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = new WeakReference(textView);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //用弱引用 先判空 避免崩溃
        if (mTextView.get() == null) {
            cancle();
            return;
        }
        mTextView.get().setEnabled(false); //设置不可点击
        mTextView.get().setText(millisUntilFinished / 1000 + " s "); //设置倒计时时间
        mTextView.get().setText(mTextView.get().getText().toString());
    }

    @Override
    public void onFinish() {
        //用软引用 先判空 避免崩溃
        if (mTextView.get() == null) {
            cancle();
            return;
        }
        mTextView.get().setText(BaseApplication.getApplication().getResources().getString(R.string.get_code_num));
        mTextView.get().setEnabled(true);//重新获得点击
    }

    public void cancle() {
        if (this != null) {
            this.cancel();
        }
    }

}
