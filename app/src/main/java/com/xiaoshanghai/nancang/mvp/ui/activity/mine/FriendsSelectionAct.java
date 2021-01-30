package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.OnFriendsSelectCallback;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.FriendsSelectionContract;
import com.xiaoshanghai.nancang.mvp.presenter.FriendsSelectionPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;


public class FriendsSelectionAct extends BaseMvpActivity<FriendsSelectionPresenter> implements FriendsSelectionContract.View, TitleBarClickCallback, OnFriendsSelectCallback {

    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.ns_view_pager)
    ViewPager nsViewPager;

    private Decks decks;

    @Override
    public int setLayoutId() {
        return R.layout.activity_friends_selection;
    }

    @Override
    protected FriendsSelectionPresenter createPresenter() {
        return new FriendsSelectionPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();

    }

    private void init() {
        mPresenter.attachView(this);
        mPresenter.initFragment(magicIndicator,nsViewPager,this);
        mTitleBar.setOnClickCallback(this);
        Bundle extras = getIntent().getExtras();
        decks = (Decks) extras.getSerializable(Constant.DECKS);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }

    @Override
    public void onFriendsSelect(MyFollowResult result) {

        if (decks == null || result == null) return;

        TipsDialog.createDialog(this, R.layout.dialog_headwear_store)
                .setText(R.id.tv_title, "赠送提示")
                .setText(R.id.tv_gift_info, "赠送 " + result.getUserName() + " " + decks.getDeckName() + "(" + decks.getUseDay() + "天)")
                .setText(R.id.tv_price, decks.getCostNumber() + (decks.getCostType() == 1 ? "金币" : decks.getCostType() == 2 ? "辣椒" : ""))
                .bindClick(R.id.tv_cancel, (v, dialog) -> {
                    dialog.dismiss();
                })
                .bindClick(R.id.tv_comit, (v, dialog) -> {
                    mPresenter.giveAway(decks.getId(), result.getUserId());

                })
                .show();

    }

    @Override
    public void onFriendsClick(MyFollowResult result) {

    }

    @Override
    public void giveAwaySuccess(Integer status) {
        if (status == 1) {
            ToastUtils.showShort("赠送成功");
            finish();
        } else {
            ToastUtils.showShort("余额不足");
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}