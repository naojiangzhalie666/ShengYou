package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.callback.OnFriendsSelectCallback;
import com.xiaoshanghai.nancang.mvp.contract.FansSelectContract;
import com.xiaoshanghai.nancang.mvp.presenter.FansSelectPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.FansSelectAdapter;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public class FansSelectFragment extends BaseMvpFragment<FansSelectPresenter> implements FansSelectContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rcy_attention)
    RecyclerView rcyAttention;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private FansSelectAdapter mAdapter;

    private OnFriendsSelectCallback mCallback;
    
    public static FansSelectFragment newInstance() {
        return new FansSelectFragment();
    }

    public void setOnFriendsSelectCallback(OnFriendsSelectCallback callback){
        this.mCallback = callback;
    }

    @Override
    protected FansSelectPresenter createPresenter() {
        return new FansSelectPresenter();
    }
    
    @Override
    public int setLayoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    protected void onLazyLoad() {
        refresh.setOnRefreshLoadMoreListener(this);

        mAdapter = new FansSelectAdapter();
        rcyAttention.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                if (mCallback!=null) {
                    mCallback.onFriendsSelect(item);
                }

            }
        });

        mPresenter.getMyFans(null);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);

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
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }




}
