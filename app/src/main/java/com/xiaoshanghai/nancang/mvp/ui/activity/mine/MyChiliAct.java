package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.MyChiliContract;
import com.xiaoshanghai.nancang.mvp.presenter.MyChiliPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyChiliAct extends BaseMvpActivity<MyChiliPresenter> implements MyChiliContract.View, TitleBarClickCallback {


    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_my_chili_num)
    TextView tvMyChiliNum;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_outlay)
    TextView tvOutlay;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_chili;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
        mPresenter.initFragments(viewpager,tvIncome,tvOutlay);
        viewpager.setCurrentItem(0);
        tvIncome.setSelected(true);
        tvOutlay.setSelected(false);
    }

    @Override
    protected MyChiliPresenter createPresenter() {
        return new MyChiliPresenter();
    }

    @Override
    public void onChiliSuccess(String count) {
        tvMyChiliNum.setText(count);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyChili();

    }

    @OnClick({R.id.tv_income, R.id.tv_outlay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_income:
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_outlay:
                viewpager.setCurrentItem(1);
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
