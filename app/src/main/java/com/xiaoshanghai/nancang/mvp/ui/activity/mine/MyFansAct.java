package com.xiaoshanghai.nancang.mvp.ui.activity.mine;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.MyFansContract;
import com.xiaoshanghai.nancang.mvp.presenter.MyFansPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyFansAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public class MyFansAct extends BaseMvpActivity<MyFansPresenter> implements MyFansContract.View, OnRefreshLoadMoreListener, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.rcy_attention)
    RecyclerView rcyAttention;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private MyFansAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_fans;
    }


    @Override
    protected MyFansPresenter createPresenter() {
        return new MyFansPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();
        mPresenter.getMyFans(null);
    }

    private void init() {
        titleBar.setOnClickCallback(this);
        refresh.setOnRefreshLoadMoreListener(this);

        mAdapter = new MyFansAdapter();
        rcyAttention.setLayoutManager(new LinearLayoutManager(this));
        rcyAttention.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

                if (position == 0) {
                    outRect.set(0, 15, 0, 8);
                } else {
                    outRect.set(0, 8, 0, 8);
                }
            }
        });

        rcyAttention.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                MyFollowResult item = mAdapter.getItem(position);
                String followEach = item.getFollowEach();
                if (followEach.equals("1")) {
                    ToastUtils.showShort("相互关注-取消关注操作");
                    mPresenter.unFollow(item.getUserId(),position);
                } else {
                    ToastUtils.showShort("需要关注");
                    mPresenter.follow(item.getUserId(),position);
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.USER_ID, mAdapter.getItem(position).getUserId());
                ActStartUtils.startAct(MyFansAct.this, HomePageAct.class, bundle);
            }
        });



    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getMyFans(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMyFans(refreshLayout);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }

    @Override
    public void refreshSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows) {
        if (refresh != null)
            refresh.finishRefresh();

        mAdapter.setList(myFollows);

        if (myFollows.size() <= 0) {
            mAdapter.setEmptyView(R.layout.fans_empty);
            if (refresh != null) {
                refresh.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (refresh != null) {
                refresh.resetNoMoreData();
            }
        }
    }

    @Override
    public void loadMoreSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows) {
        if (myFollows.size() <= 0) {

            if (refresh != null)
                refresh.finishLoadMoreWithNoMoreData();
        } else {
            if (refresh != null)
                refresh.finishLoadMore();
            mAdapter.addData(myFollows);
        }
    }

    @Override
    public void followSuccess(String status, int position) {
        if (status.equals("1")) {
            MyFollowResult item = mAdapter.getItem(position);
            item.setFollowEach("1");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void unFollowSuccess(String status, int position) {
        if (status.equals("1")) {
            MyFollowResult item = mAdapter.getItem(position);
            item.setFollowEach("0");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}
