package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.MyGoldContract;
import com.xiaoshanghai.nancang.mvp.presenter.MyGoldPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyGoldAct extends BaseMvpActivity<MyGoldPresenter> implements MyGoldContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_gold;
    }

    @Override
    protected MyGoldPresenter createPresenter() {
        return new MyGoldPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyGoldNum();
    }

    @OnClick({R.id.tv_exchange, R.id.rl_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_exchange:
                ActStartUtils.startAct(this, GoldExchangeAct.class);
                break;
            case R.id.rl_recharge:
                ActStartUtils.startAct(this, GoldRechargeAct.class);
                break;
        }
    }

    @Override
    public void onSuccess(Double goldNum) {
        tvGoldNum.setText(String.format("%.1f",goldNum));
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
