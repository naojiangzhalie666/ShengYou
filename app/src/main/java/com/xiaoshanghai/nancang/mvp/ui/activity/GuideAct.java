package com.xiaoshanghai.nancang.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseActivity;
import com.xiaoshanghai.nancang.callback.GuideCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.LoginActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.MainActivity;
import com.xiaoshanghai.nancang.mvp.ui.adapter.GuideAdapter;
import com.xiaoshanghai.nancang.net.bean.GuidBean;
import com.xiaoshanghai.nancang.utils.AppUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideAct extends BaseActivity implements GuideCallback {


    @BindView(R.id.banner)
    Banner mBanner;

    private GuideAdapter mAdapter;

    private List<GuidBean> mGuids = new ArrayList<>();

    private String token;

    @Override
    public int setLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        SPUtils.getInstance().put(Constant.VERSION, AppUtils.getAppVersionName(this));

        token = SPUtils.getInstance().getString(SpConstant.APP_TOKEN);

        GuidBean guidaOne = new GuidBean(0, R.drawable.img_guid_1);
        GuidBean guidaTwo = new GuidBean(1, R.drawable.img_guid_2);
        GuidBean guidaThree = new GuidBean(2, R.drawable.img_guid_3);

        mGuids.add(guidaOne);
        mGuids.add(guidaTwo);
        mGuids.add(guidaThree);

        mAdapter = new GuideAdapter(this, mGuids,this);

        initBanner();
    }

    private void initBanner() {
        mBanner.addBannerLifecycleObserver(this);
        mBanner.isAutoLoop(false);
        mBanner.setAdapter(mAdapter, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null)
            mBanner.destroy();
    }

    @Override
    public void onEnterClick() {

        if (TextUtils.isEmpty(token) || SPUtils.getInstance().getUserInfo() == null) {
            startActivity(new Intent(this, LoginActivity.class));

        } else {
            startActivity(new Intent(this, MainActivity.class));

        }

        finish();
    }
}