package com.xiaoshanghai.nancang.mvp.ui.fragment.square;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.FamilyContract;
import com.xiaoshanghai.nancang.mvp.presenter.FamilyPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.FamilyCreateAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.FamilyMemberAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.FamilySquareAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.StartCommendAdapter;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyFragment extends BaseMvpFragment<FamilyPresenter> implements FamilyContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_family_square)
    RelativeLayout rlFamilySquare;
    @BindView(R.id.rl_create_family)
    RelativeLayout rlCreateFamily;
    @BindView(R.id.ll_my_family)
    LinearLayout llMyFamily;
    @BindView(R.id.civ_family_avater)
    CircleImageView mFamilyAvater;
    @BindView(R.id.tv_famliy_name)
    TextView mTvFamilyName;
    @BindView(R.id.tv_family_id)
    TextView mTvFamilyId;
    @BindView(R.id.tv_family_member)
    TextView mTvFamilyMember;
    @BindView(R.id.rl_view)
    RecyclerView mStartCommend;

    private StartCommendAdapter mAdapter;

    private UserBean userInfo;

    private MyFamilyResult mResult;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_family;
    }

    @Override
    protected FamilyPresenter createPresenter() {
        return new FamilyPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        userInfo = SPUtils.getInstance().getUserInfo();
        mPresenter.getFamilyInfo(userInfo.getId(), null);

        mAdapter = new StartCommendAdapter();
        mStartCommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStartCommend.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            StartRecommendResult item = mAdapter.getItem(position);

            Bundle bundle = new Bundle();
            bundle.putString(Constant.FAMILY_ID, item.getClanId());
            bundle.putSerializable(Constant.MY_FAMILY,mResult);
            ActStartUtils.startAct(FamilyFragment.this.getActivity(), FamilyMemberAct.class, bundle);

        });


    }

    @Override
    public void familySuccess(MyFamilyResult result, RefreshLayout refreshLayout) {

        if (result == null) {

            rlCreateFamily.setVisibility(View.VISIBLE);
            llMyFamily.setVisibility(View.GONE);

        } else {
            this.mResult = result;
            llMyFamily.setVisibility(View.VISIBLE);
            rlCreateFamily.setVisibility(View.GONE);
            setMyFamily(result);

        }

        mPresenter.getStartRecommend(refreshLayout);
    }

    private void setMyFamily(MyFamilyResult result) {
        GlideAppUtil.loadImage(getActivity(),result.getClanPicture(),mFamilyAvater);
        mTvFamilyName.setText(result.getClanName());
        mTvFamilyId.setText(result.getClanNumber()+"");
        mTvFamilyMember.setText(result.getMember()+"");
    }

    @Override
    public void onError(String msg, RefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        ToastUtils.showShort(msg);
    }

    @Override
    public void recommendSuccess(List<StartRecommendResult> startList, RefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        mAdapter.setList(startList);
    }

    @OnClick({R.id.rl_family_square, R.id.rl_create_family, R.id.ll_my_family})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_family_square:
                Bundle myFamilyBundle = new Bundle();
                myFamilyBundle.putSerializable(Constant.MY_FAMILY,mResult);
                ActStartUtils.startAct(getActivity(), FamilySquareAct.class,myFamilyBundle);
                break;
            case R.id.rl_create_family:
                ActStartUtils.startAct(getActivity(), FamilyCreateAct.class);
                break;
            case R.id.ll_my_family:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.FAMILY_ID, mResult.getClanId());
                bundle.putSerializable(Constant.MY_FAMILY,mResult);
                ActStartUtils.startAct(FamilyFragment.this.getActivity(), FamilyMemberAct.class, bundle);
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (userInfo == null) {
            refreshLayout.finishRefresh();
        } else {
            mPresenter.getFamilyInfo(userInfo.getId(), refreshLayout);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {


    }
}
