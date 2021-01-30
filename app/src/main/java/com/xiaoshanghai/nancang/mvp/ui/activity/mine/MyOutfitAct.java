package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.MyOutfitConstract;
import com.xiaoshanghai.nancang.mvp.presenter.MyOutfitPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

public class MyOutfitAct extends BaseMvpActivity<MyOutfitPresenter> implements MyOutfitConstract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.mag_index)
    MagicIndicator magIndex;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_outfit;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);


        mPresenter.initFragments(viewpager,magIndex);
    }

    @Override
    protected MyOutfitPresenter createPresenter() {
        return new MyOutfitPresenter();
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}
