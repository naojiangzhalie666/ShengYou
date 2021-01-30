package com.xiaoshanghai.nancang.mvp.ui.fragment.msg;

import android.os.Bundle;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.mvp.contract.MsgContract;
import com.xiaoshanghai.nancang.mvp.model.MsgPresenter;
import com.xiaoshanghai.nancang.view.ScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

public class MsgFragment extends BaseMvpFragment<MsgPresenter> implements MsgContract.View {

    @BindView(R.id.magicindicator)
    MagicIndicator magicindicator;
    @BindView(R.id.s_view_pager)
    ScrollViewPager mViewPager;

    @Override
    protected MsgPresenter createPresenter() {
        return new MsgPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPresenter.attachView(this);

        mPresenter.initFragments(magicindicator,mViewPager);

    }
}
