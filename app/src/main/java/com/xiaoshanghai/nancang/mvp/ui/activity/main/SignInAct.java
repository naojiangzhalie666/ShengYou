package com.xiaoshanghai.nancang.mvp.ui.activity.main;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.SignInContract;
import com.xiaoshanghai.nancang.mvp.presenter.SignInPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.ChiliProgressbar;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.SignDayEntity;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInAct extends BaseMvpActivity<SignInPresenter> implements SignInContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.iv_sign_in)
    ImageView ivSignIn;
    @BindView(R.id.progress_signin)
    public ChiliProgressbar mProgressbar;


    private int signDay = 0;
    private boolean isSign = false;

    @Override
    public int setLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected SignInPresenter createPresenter() {
        return new SignInPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();
        mPresenter.getSignReward();
        mPresenter.getSignDay();
    }

    private void init() {
        titleBar.setOnClickCallback(this);
    }


    @OnClick({R.id.iv_sign_in})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sign_in:
                if (!isSign) {
                    isSign = true;
                    signDay++;
                    mPresenter.sign();
                }
                break;
        }
    }

    @Override
    public void onSignRewardSuccess(List<SignRewardEntity> results) {
        mProgressbar.setSignResult(results);
    }

    @Override
    public void onSignDaySuccess(SignDayEntity signDayResult) {
        isSign = signDayResult.getHasSign() == 1;
        signDay = signDayResult.getDays();

        if (isSign) {
            ivSignIn.setImageResource(R.mipmap.icon_sign_off);
        } else {
            ivSignIn.setImageResource(R.mipmap.icon_sign_button);
        }

        mProgressbar.setProgress(isSign, signDay);
    }

    @Override
    public void onSginSuccess(Integer status) {
        if (status == 1) {
            mProgressbar.setProgress(isSign, signDay);
        } else {
            isSign = false;
            signDay--;
        }

        if (isSign) {
            ivSignIn.setImageResource(R.mipmap.icon_sign_off);
        } else {
            ivSignIn.setImageResource(R.mipmap.icon_sign_button);
        }

    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }


    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}