package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.BindEntity;
import com.xiaoshanghai.nancang.net.bean.NewGoldResult;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;

public class CashSuccessAct extends BaseActivity implements TitleBarClickCallback {


    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.rl_wx)
    RelativeLayout mRlWx;
    @BindView(R.id.tv_wx_num)
    TextView mTvWxNum;
    @BindView(R.id.rl_zfb)
    RelativeLayout mRlZfb;
    @BindView(R.id.tv_zfb_num)
    TextView mTvZfbNum;
    @BindView(R.id.rl_yhk)
    RelativeLayout mRlYhk;
    @BindView(R.id.tv_yhk_num)
    TextView mTvYhkNum;
    @BindView(R.id.rl_qbd)
    RelativeLayout mRlQbd;

    private NewGoldResult goldResult;
    private BindEntity mResult;

    @Override
    public int setLayoutId() {
        return R.layout.activity_cash_success;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mTitleBar.setOnClickCallback(this);
        Bundle extras = getIntent().getExtras();
        goldResult = (NewGoldResult) extras.getSerializable(Constant.CASH_KEY);
        mResult = (BindEntity) extras.getSerializable(Constant.CASH_TYPE);
        mTvMoney.setText(goldResult.getAmount()+"");

        setBound(mResult);
    }

    private void setBound(BindEntity result) {
        if (result != null) {
            int type = result.getType();
            switch (type) {
                case 1:
                    mRlYhk.setVisibility(View.VISIBLE);
                    mRlZfb.setVisibility(View.GONE);
                    mRlWx.setVisibility(View.GONE);
                    mRlQbd.setVisibility(View.GONE);
                    mTvYhkNum.setText(result.getBankType() + result.getAccount());
                    break;
                case 2:
                    mRlYhk.setVisibility(View.GONE);
                    mRlZfb.setVisibility(View.VISIBLE);
                    mRlWx.setVisibility(View.GONE);
                    mRlQbd.setVisibility(View.GONE);
                    mTvZfbNum.setText(result.getAccount());
                    break;
                case 3:
                    mRlYhk.setVisibility(View.GONE);
                    mRlZfb.setVisibility(View.GONE);
                    mRlWx.setVisibility(View.VISIBLE);
                    mRlQbd.setVisibility(View.GONE);
                    mTvWxNum.setText(result.getAccount());
                    break;
            }
        } else {
            mRlYhk.setVisibility(View.GONE);
            mRlZfb.setVisibility(View.GONE);
            mRlWx.setVisibility(View.GONE);
            mRlQbd.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_know)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_know:
                finish();
                break;
        }
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}