package com.xiaoshanghai.nancang.mvp.ui.fragment.msg;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.mvp.contract.ContactContract;
import com.xiaoshanghai.nancang.mvp.presenter.ContactPresenter;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

public class ContactPersonFragment extends BaseMvpFragment<ContactPresenter> implements ContactContract.View {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.ns_view_pager)
    ViewPager nsViewPager;

    public static ContactPersonFragment newInstance() {
        return new ContactPersonFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_contact_person;
    }

    @Override
    protected ContactPresenter createPresenter() {
        return new ContactPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mPresenter.initFragment(magicIndicator,nsViewPager);
    }
}
