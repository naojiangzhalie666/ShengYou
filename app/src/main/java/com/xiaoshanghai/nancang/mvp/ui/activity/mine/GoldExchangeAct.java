package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.GoldExchangeContract;
import com.xiaoshanghai.nancang.mvp.presenter.GoldExchangePresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.utils.DecimalFilter;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class GoldExchangeAct extends BaseMvpActivity<GoldExchangePresenter> implements GoldExchangeContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_diamond_num)
    TextView tvDiamondNum;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    @BindView(R.id.et_diamond)
    EditText etDiamond;
    @BindView(R.id.et_gold)
    TextView etGold;
    @BindView(R.id.bt_exchange)
    Button mBtExchange;
    @BindView(R.id.tv_prompt)
    TextView mTvPrompt;

    private Double gold;
    private Double diamond;

    @Override
    public int setLayoutId() {
        return R.layout.activity_gold_exchange;
    }

    @Override
    protected GoldExchangePresenter createPresenter() {
        return new GoldExchangePresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyDiamondNum();
        mPresenter.getMyGoldNum();
    }

    @Override
    public void myGoldSuccess(Double goldNum) {
        gold = goldNum;
        tvGoldNum.setText(String.format("%.1f", goldNum));
    }

    @Override
    public void onDiamondSuccess(Double diamondNum) {
        diamond = diamondNum;
        etDiamond.setFilters(new InputFilter[]{new DecimalFilter()});
        tvDiamondNum.setText(String.format("%.1f", diamondNum));

        int i = new Double(diamondNum).intValue();

        mTvPrompt.setText("请输入兑换的钻石数量(最多可兑换" + i + ")");
    }

    @Override
    public void onChangeSuccess(Integer status) {
        if (status == 1) {
            mPresenter.getMyDiamondNum();
            mPresenter.getMyGoldNum();
            etDiamond.setText("");
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick(R.id.bt_exchange)
    public void onViewClicked() {
        String num = etDiamond.getText().toString().trim();
        mPresenter.goldChange(num);
    }

    @OnTextChanged(R.id.et_diamond)
    void onChanged(Editable editable) {
        mBtExchange.setEnabled(etDiamond.length() > 0 && Double.valueOf(etDiamond.getText().toString().trim()) < diamond && Double.valueOf(etDiamond.getText().toString().trim()) > 0);
        etGold.setText(etDiamond.length() > 0 ? etDiamond.getText().toString().trim() + "金币" : "");
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}
