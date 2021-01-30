package com.xiaoshanghai.nancang.wxapi;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @BindView(R.id.title_bg)
    ImageView mIvTitleBg;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.group_1)
    Group mG1;

    private IWXAPI api;

    @Override
    public int setLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                mTvError.setVisibility(View.GONE);
                mIvTitleBg.setImageResource(R.mipmap.img_pay_result);
                mG1.setVisibility(View.GONE);
            } else {
                mTvError.setVisibility(View.VISIBLE);
                mTvError.setText("支付失败");
                mG1.setVisibility(View.GONE);
                mIvTitleBg.setImageResource(R.drawable.shape_pay_result);
            }
        }
    }


}