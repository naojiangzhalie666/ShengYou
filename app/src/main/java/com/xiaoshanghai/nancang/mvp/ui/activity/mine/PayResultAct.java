package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.AlipayTradeAppPayResponse;
import com.xiaoshanghai.nancang.net.bean.PayResultEntity;

import butterknife.BindView;

public class PayResultAct extends BaseMvpActivity implements TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    @Override
    public int setLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitleBar.setOnClickCallback(this);
        Bundle extras = getIntent().getExtras();
        String payResult = extras.getString(Constant.PAY_RESULT);
        PayResultEntity payResultEntity = new Gson().fromJson(payResult, PayResultEntity.class);
        if (payResultEntity != null) {
            AlipayTradeAppPayResponse alipay_trade_app_pay_response = payResultEntity.getAlipay_trade_app_pay_response();
            setPayResult(alipay_trade_app_pay_response);
        }

    }

    private void setPayResult(AlipayTradeAppPayResponse entity) {
        mTvMoney.setText(entity.getTotal_amount());
        mTvTime.setText(entity.getTimestamp());
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}