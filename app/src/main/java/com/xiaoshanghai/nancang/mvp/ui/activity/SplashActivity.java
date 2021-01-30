package com.xiaoshanghai.nancang.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.SplashContract;
import com.xiaoshanghai.nancang.mvp.presenter.SplashPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.LoginActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.MainActivity;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.AppUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;

import io.reactivex.annotations.Nullable;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.View {


    private String token;

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mPresenter.startMainActivity();
        token = SPUtils.getInstance().getString(SpConstant.APP_TOKEN);
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }


    @Override
    public void startMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String version = SPUtils.getInstance().getString(Constant.VERSION);
                String appVersionName = AppUtils.getAppVersionName(SplashActivity.this);
                if (TextUtils.isEmpty(version) || !version.equals(appVersionName)) {
                    ActStartUtils.startAct(SplashActivity.this, GuideAct.class);
                } else {

                    if (TextUtils.isEmpty(token) || SPUtils.getInstance().getUserInfo() == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    }
                }
                finish();


            }
        }, 1500);
    }
}
