package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.OnCarTestDive;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.HeadgearContract;
import com.xiaoshanghai.nancang.mvp.presenter.HeadgearPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.AvatarView;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.opensource.svgaplayer.SVGAImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class HeadgearAct extends BaseMvpActivity<HeadgearPresenter> implements HeadgearContract.View, TitleBarClickCallback, OnCarTestDive {

    @BindView(R.id.title_bar)
    TitleBarView mTitle;
    @BindView(R.id.av_avatar)
    AvatarView mAvatar;
    @BindView(R.id.tv_headgear)
    TextView tvHeadgear;
    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rl_svag)
    RelativeLayout mRlSvag;
    @BindView(R.id.svga)
    SVGAImageView mSvga;

    private MineReslut mResult;

    private boolean isMine = false;

    private String userId;

    private  String mChatType;
    private  String mGroupId;

    @Override
    public int setLayoutId() {
        return R.layout.activity_headgear;
    }

    @Override
    protected HeadgearPresenter createPresenter() {
        return new HeadgearPresenter();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();

        if (mResult != null) {
            setMineResult(mResult);
            mPresenter.initFragments(viewpager, tvHeadgear, tvCar, mResult, "","",this);
            if (!isMine) {
                viewpager.setCurrentItem(1);
                tvHeadgear.setSelected(false);
                tvCar.setSelected(true);
            } else {
                viewpager.setCurrentItem(0);
                tvHeadgear.setSelected(true);
                tvCar.setSelected(false);
            }
        } else {
            Bundle extras = getIntent().getExtras();
            userId = extras.getString(Constant.USER_ID);
            mChatType= extras.getString(Constant.CHAT_TYPE);
            mGroupId = extras.getString(Constant.PUBLIC_CHAT_GROUP);
            mPresenter.getMine(userId);
        }

    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        isMine = extras.getBoolean(Constant.IS_SELF);
        mResult = (MineReslut) extras.getSerializable(Constant.MINE_RESLUT);
        mTitle.setOnClickCallback(this);

    }

    private void setMineResult(MineReslut result) {
        Decks headwear = result.getHeadwear();
        String deckUrl = "";
        if (headwear != null) {
            deckUrl = headwear.getDeckUrl();
        }
        mAvatar.setAvatarAndHeadear(result.getUserPicture(), deckUrl);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        ActStartUtils.startAct(this, MyOutfitAct.class);
    }

    @OnClick({R.id.tv_headgear, R.id.tv_car, R.id.iv_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_headgear:
                tvHeadgear.setSelected(true);
                tvCar.setSelected(false);
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_car:
                tvHeadgear.setSelected(false);
                tvCar.setSelected(true);
                viewpager.setCurrentItem(1);
                break;
            case R.id.iv_stop:
                mPresenter.stopAnimation(mSvga);
                mRlSvag.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onCarTestClick(int type, Decks decks) {

        if (type == Constant.CAR_CLICK) {
            mRlSvag.setVisibility(View.VISIBLE);
            mPresenter.animation(decks.getDeckUrl(), mSvga);
        } else if (type == Constant.HEADWEAR_CLICK) {
            mAvatar.setAvatarAndHeadear(mResult.getUserPicture(), decks.getDeckUrl());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void MineSuccess(MineReslut result) {
        mResult = result;
        setMineResult(mResult);
        mPresenter.initFragments(viewpager, tvHeadgear, tvCar, mResult,mChatType,mGroupId, this);

        viewpager.setCurrentItem(0);
        tvHeadgear.setSelected(true);
        tvCar.setSelected(false);
    }

    @Override
    public void onError(String msg) {

    }
}
