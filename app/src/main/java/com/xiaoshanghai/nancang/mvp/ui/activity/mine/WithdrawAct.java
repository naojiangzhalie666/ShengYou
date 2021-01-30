package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.URLConstant;
import com.xiaoshanghai.nancang.mvp.contract.WithdrawContract;
import com.xiaoshanghai.nancang.mvp.presenter.WithdrawPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.NewGoldRechargeAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.CashHeader;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.BindEntity;
import com.xiaoshanghai.nancang.net.bean.NewGoldResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawAct extends BaseMvpActivity<WithdrawPresenter> implements WithdrawContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.rcy_gold)
    RecyclerView rcyGold;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.ll_handling_fee)
    LinearLayout llHandlingFee;
    @BindView(R.id.tv_rmb_num)
    TextView tvRmbNum;
    @BindView(R.id.bt_cash)
    Button btCash;
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


    private NewGoldRechargeAdapter mAdapter;
    private NewGoldResult goldResult;
    private CashHeader headerView;
    private BindEntity mResult;

    @Override
    public int setLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();
        initPayType();
        mPresenter.getCashList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBoundPay();
        mPresenter.getMyDiamondNum();
    }

    private void initPayType() {
        mRlWx.setVisibility(View.GONE);
        mRlZfb.setVisibility(View.GONE);
        mRlYhk.setVisibility(View.GONE);
        mRlQbd.setVisibility(View.VISIBLE);
    }

    private void init() {
        titleBar.setOnClickCallback(this);
        mAdapter = new NewGoldRechargeAdapter();
        headerView = new CashHeader(this);
        mAdapter.setHeaderView(headerView);
        rcyGold.setLayoutManager(new LinearLayoutManager(this));
        rcyGold.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

                if (position == 0) {
                    outRect.set(0, 24, 0, 8);
                } else {
                    outRect.set(0, 8, 0, 8);
                }
            }
        });
        rcyGold.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                NewGoldResult item = mAdapter.getItem(position);

                if (goldResult == null) {
                    goldResult = item;
                    item.setSelect(true);
                } else {
                    goldResult.setSelect(false);
                    item.setSelect(true);
                    goldResult = item;
                }

                mAdapter.notifyDataSetChanged();

                setCashResult();
            }
        });

    }

    @Override
    protected WithdrawPresenter createPresenter() {
        return new WithdrawPresenter();
    }

    @Override
    public void cashSuccess(List<NewGoldResult> results) {
        if (results.size() > 0) {
            goldResult = results.get(0);
            goldResult.setSelect(true);
            setCashResult();
        }
        mAdapter.setList(results);
    }

    @Override
    public void onDiamondSuccess(Double diamondNum) {
        headerView.setCash(diamondNum);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onBoundSuccess(BindEntity result) {
        mResult = result;
        setBound(result);
    }

    @Override
    public void onBoundError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onWithdrawSuccess(int status) {
        if (status == 1) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.CASH_KEY,goldResult);
            bundle.putSerializable(Constant.CASH_TYPE,mResult);
            ActStartUtils.startAct(this,CashSuccessAct.class,bundle);
        } else {
            ToastUtils.showShort("提现失败");
        }
        mPresenter.getCashList();
    }

    @Override
    public void onWithdrawError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        ActStartUtils.startAct(this, CashRecordAct.class);
    }

    private void setCashResult() {
        if (goldResult == null) {
            llHandlingFee.setVisibility(View.GONE);
            tvRmbNum.setVisibility(View.GONE);
            btCash.setEnabled(false);
        } else {
            llHandlingFee.setVisibility(View.VISIBLE);
            tvRmbNum.setVisibility(View.VISIBLE);
            tvFee.setText(goldResult.getCharge()+"");
            tvRmbNum.setText(String.valueOf(goldResult.getAmount()));
            btCash.setEnabled(true);
        }

    }


    @OnClick({R.id.bt_cash, R.id.tv_rule, R.id.rl_wx, R.id.rl_zfb, R.id.rl_yhk, R.id.rl_qbd,R.id.bt_goto_gold})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_goto_gold:
                ActStartUtils.startAct(this,GoldExchangeAct.class);
                break;
            case R.id.bt_cash:

                if (goldResult == null) {
                    ToastUtils.showShort("请选择提现钻石");
                    return;
                }

                if (mResult == null) {
                    ToastUtils.showShort("请绑定提现账号");
                    return;
                }

                new TipsDialog().createDialog(this, R.layout.dialog_withdraw)
                        .setText(R.id.tv_title_2, "是否提现" + goldResult.getJewelNumber() + "钻石")
                        .bindClick(R.id.tv_cancel, (v, dialog) -> dialog.dismiss())
                        .bindClick(R.id.tv_confirm, (v, dialog) -> {
                            mPresenter.withdraw(goldResult.getId());
                            dialog.dismiss();

                        })
                        .show();

                break;
            case R.id.tv_rule:
                Bundle userBundel = new Bundle();
                userBundel.putString(Constant.WEB_URL, URLConstant.WITHDRAW_URL);
                userBundel.putString(Constant.WEB_TITLE, "提现规则");
                ActStartUtils.startAct(this, WebActivity.class, userBundel);
                break;
            case R.id.rl_wx:
            case R.id.rl_zfb:
            case R.id.rl_yhk:
                ActStartUtils.startAct(this, BoundPayAct.class);
                break;
            case R.id.rl_qbd:
                ActStartUtils.startAct(this, BindPayAct.class);
                break;
        }
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
}
