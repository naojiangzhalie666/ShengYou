package com.xiaoshanghai.nancang.mvp.ui.activity.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.MyDiamondContract;
import com.xiaoshanghai.nancang.mvp.presenter.MyDiamondPresenter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.DiamondExpensesFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.DiamondIncomeFragment;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class MyDiamondAct extends BaseMvpActivity<MyDiamondPresenter> implements MyDiamondContract.View, TitleBarClickCallback {


    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_diamond_num)
    TextView tvDiamondNum;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_expenses)
    TextView tvExpenses;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    private DiamondIncomeFragment incomeFragment;

    private DiamondExpensesFragment expensesFragment;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_diamond;
    }

    @Override
    protected MyDiamondPresenter createPresenter() {
        return new MyDiamondPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
        selectIncome();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyDiamondNum();
    }

    @OnClick({R.id.tv_income, R.id.tv_expenses, R.id.bt_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_income:
                selectIncome();
                break;
            case R.id.tv_expenses:
                selectExpenses();
                break;
            case R.id.bt_cash:
                ActStartUtils.startAct(this, WithdrawAct.class);
                break;
        }
    }

    private void selectIncome() {
        tvIncome.setTextColor(getResources().getColor(R.color.color_black));
        tvIncome.setTextSize(20);
        tvExpenses.setTextColor(getResources().getColor(R.color.color_484848));
        tvExpenses.setTextSize(16);
        if (incomeFragment == null)
            incomeFragment = new DiamondIncomeFragment();
        mPresenter.replace(incomeFragment);
    }

    private void selectExpenses() {
        tvExpenses.setTextColor(getResources().getColor(R.color.color_black));
        tvExpenses.setTextSize(20);
        tvIncome.setTextColor(getResources().getColor(R.color.color_484848));
        tvIncome.setTextSize(16);
        if (expensesFragment == null)
            expensesFragment = new DiamondExpensesFragment();
        mPresenter.replace(expensesFragment);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }

    @Override
    public void onDiamondSuccess(Double diamondNum) {
        tvDiamondNum.setText(String.format("%.1f",diamondNum));
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}
