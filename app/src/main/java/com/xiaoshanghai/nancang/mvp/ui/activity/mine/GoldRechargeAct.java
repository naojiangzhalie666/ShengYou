package com.xiaoshanghai.nancang.mvp.ui.activity.mine;


import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.GoldRechargeContract;
import com.xiaoshanghai.nancang.mvp.presenter.GoldRechargePresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.GoldRechargeAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.GoldRechargeHeader;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult;
import com.xiaoshanghai.nancang.net.bean.GoldResult;
import com.xiaoshanghai.nancang.net.bean.PayResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GoldRechargeAct extends BaseMvpActivity<GoldRechargePresenter> implements GoldRechargeContract.View, TitleBarClickCallback {


    @BindView(R.id.iv_wx_select)
    ImageView ivWxSelect;
    @BindView(R.id.iv_al_select)
    ImageView ivAlSelect;
    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.rcy_gold)
    RecyclerView rcyGold;
    @BindView(R.id.rl_recharge)
    RelativeLayout mRlRecharge;

    private GoldRechargeAdapter mAdapter;
    private GoldResult goldResult;
    private GoldRechargeHeader headerView;

    private int mWxOrAL = 2;

    private IWXAPI api;

    private static final int SDK_PAY_FLAG = 1;
//    private static final int SDK_AUTH_FLAG = 2;


    @Override
    public int setLayoutId() {
        return R.layout.activity_gold_recharge;
    }


    @Override
    protected GoldRechargePresenter createPresenter() {
        return new GoldRechargePresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getGoldList(SPUtils.getInstance().getUserInfo().getId());
        mPresenter.getMyGoldNum();
    }

    private void init() {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
        mAdapter = new GoldRechargeAdapter(0);
        headerView = new GoldRechargeHeader(this);
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
                if (goldResult != null) goldResult.setSelect(false);
                goldResult = mAdapter.getItem(position);
                goldResult.setSelect(true);
                mAdapter.notifyDataSetChanged();
            }
        });



    }

    @OnClick({R.id.rl_wx, R.id.rl_al, R.id.rl_exchange, R.id.rl_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_wx:
                mWxOrAL = 2;
                selectType(mWxOrAL);
                break;
            case R.id.rl_al:
                mWxOrAL = 1;
                selectType(mWxOrAL);
                break;
            case R.id.rl_exchange:
                break;
            case R.id.rl_recharge:
                if (goldResult!=null) {
                    mPresenter.buyGold(mWxOrAL, goldResult.getId(), mRlRecharge);
                }

                break;
        }
    }

    private void selectType(int select) {
        if (select == 2) {
            ivWxSelect.setImageResource(R.mipmap.icon_gold_select);
            ivAlSelect.setImageResource(R.mipmap.icon_unselect);
        } else if (select == 1) {
            ivWxSelect.setImageResource(R.mipmap.icon_unselect);
            ivAlSelect.setImageResource(R.mipmap.icon_gold_select);
        }
    }

    @Override
    public void myGoldSuccess(Double goldNum) {
        headerView.setMyGoldNum(goldNum);
    }

    @Override
    public void goldListSuccess(List<GoldResult> golds) {
        if (golds.size() > 0) {
            goldResult = golds.get(0);
            goldResult.setSelect(true);
        }
        mAdapter.setList(golds);
    }

    @Override
    public void buyGoldSuccess(BuyGoldResult result) {
        if (mWxOrAL == 1) {

            mPresenter.aliPay(mHandler, result.getOrderString(),SDK_PAY_FLAG);

        } else if (mWxOrAL == 2) {
            mPresenter.weCharPay(api, result);
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
        ActStartUtils.startAct(this,RechargeRecordAct.class);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.PAY_RESULT,resultInfo);
                        ActStartUtils.startAct(GoldRechargeAct.this,PayResultAct.class,bundle);
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                        ToastUtils.showShort("支付失败");
                    }
                    break;
                }
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
//                    } else {
//                        // 其他状态值则为授权失败
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
//                    }
//                    break;
//                }
                default:
                    break;
            }
        }

        ;
    };
}
