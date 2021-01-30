package com.xiaoshanghai.nancang.mvp.ui.activity.square;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.FamilySquareContract;
import com.xiaoshanghai.nancang.mvp.presenter.FamilySquarePresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.FamilyRankAdapter;
import com.xiaoshanghai.nancang.net.bean.FamilyRankResult;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilySquareAct extends BaseMvpActivity<FamilySquarePresenter> implements FamilySquareContract.View, OnRefreshListener {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rc_family)
    RecyclerView mRcFamily;

    private FamilyRankAdapter mAdapter;

    private MyFamilyResult myFamily;

    @Override
    public int setLayoutId() {
        return R.layout.activity_family_square;
    }

    @Override
    protected FamilySquarePresenter createPresenter() {
        return new FamilySquarePresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        myFamily = (MyFamilyResult) extras.getSerializable(Constant.MY_FAMILY);

        mPresenter.attachView(this);
        refreshLayout.setOnRefreshListener(this);
        mAdapter = new FamilyRankAdapter();
        mRcFamily.setLayoutManager(new LinearLayoutManager(this));
        mRcFamily.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            FamilyRankResult item = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.FAMILY_ID, item.getId());
            bundle.putSerializable(Constant.MY_FAMILY,myFamily);
            ActStartUtils.startAct(FamilySquareAct.this, FamilyMemberAct.class, bundle);
        });

        mPresenter.getFamilyRank(null);
    }


    @OnClick({R.id.iv_black, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                finish();
                break;
            case R.id.tv_search:

                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFamilyRank(refreshLayout);
    }

    @Override
    public void familyRankSuccess(List<FamilyRankResult> results, RefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }

        mAdapter.setList(results);
    }

    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        ToastUtils.showShort(msg);
    }
}
