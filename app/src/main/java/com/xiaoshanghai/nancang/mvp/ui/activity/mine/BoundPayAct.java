package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.BoundPayContract;
import com.xiaoshanghai.nancang.mvp.presenter.BoundPayPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.BindEntity;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

public class BoundPayAct extends BaseMvpActivity<BoundPayPresenter> implements BoundPayContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.con_bind_wx)
    ConstraintLayout conBindWx;
    @BindView(R.id.tv_go_bind)
    TextView tvGoBind;
    @BindView(R.id.con_bind_zfb)
    ConstraintLayout conBindZfb;
    @BindView(R.id.tv_go_bind_zfb)
    TextView tvGoBindZfb;
    @BindView(R.id.con_bind_yhk)
    ConstraintLayout conBindYhk;
    @BindView(R.id.tv_yhk_num)
    TextView tvYhkNum;
    @BindView(R.id.tv_go_bind_yhk)
    TextView tvGoBindYhk;

    @Override
    public int setLayoutId() {
        return R.layout.activity_bound_pay;
    }

    @Override
    protected BoundPayPresenter createPresenter() {
        return new BoundPayPresenter();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mTitleBar.setOnClickCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBoundPay();
    }

    @Override
    public void onBoundSuccess(BindEntity result) {
        if (result != null) {
            setBound(result);
        }
    }

    @Override
    public void onBoundError(String msg) {
        ToastUtils.showShort(msg);
    }

    private void setBound(BindEntity result) {
        int type = result.getType();
        switch (type) {
            case 1:
                conBindYhk.setVisibility(View.VISIBLE);
                conBindZfb.setVisibility(View.GONE);
                conBindWx.setVisibility(View.GONE);
                tvYhkNum.setText(result.getBankType());
                tvGoBindYhk.setText(result.getAccount());
                break;
            case 2:
                conBindZfb.setVisibility(View.VISIBLE);
                conBindYhk.setVisibility(View.GONE);
                conBindWx.setVisibility(View.GONE);
                tvGoBindZfb.setText(result.getAccount());
                break;
            case 3:
                conBindWx.setVisibility(View.VISIBLE);
                conBindZfb.setVisibility(View.GONE);
                conBindYhk.setVisibility(View.GONE);
                tvGoBind.setText(result.getAccount());
                break;
        }
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        ActStartUtils.startAct(this, BindPayAct.class);
    }
}