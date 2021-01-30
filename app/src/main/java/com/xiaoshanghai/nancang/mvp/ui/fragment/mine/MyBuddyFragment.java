package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

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
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.callback.OnFriendsSelectCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.BuddySelectContract;
import com.xiaoshanghai.nancang.mvp.presenter.BuddySelectPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyBuddyAdapter;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public class MyBuddyFragment extends BaseMvpFragment<BuddySelectPresenter> implements BuddySelectContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rcy_friend)
    RecyclerView rcyFriend;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private MyBuddyAdapter mAdapter;

    private OnFriendsSelectCallback mCallback;

    public static MyBuddyFragment newInstance() {
        return new MyBuddyFragment();
    }

    public void setOnFriendsSelectCallback(OnFriendsSelectCallback callback){
        this.mCallback = callback;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_buddy;
    }


    @Override
    protected BuddySelectPresenter createPresenter() {
        return new BuddySelectPresenter();
    }

    @Override
    protected void onLazyLoad() {
        Bundle arguments = getArguments();
        String buddyKey = arguments.getString(Constant.BUDDY_KEY);
        mAdapter = new MyBuddyAdapter(buddyKey);
        rcyFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcyFriend.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        rcyFriend.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                MyFollowResult item = mAdapter.getItem(position);
                if (mCallback!=null) {
                    mCallback.onFriendsSelect(item);
                }
            }
        });

        if (buddyKey.equals(Constant.PUBLIC_CHAT)){
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    MyFollowResult item = mAdapter.getItem(position);
                    if (mCallback!=null) {
                        mCallback.onFriendsClick(item);
                    }
                }
            });
        }

        mPresenter.getMyFriends(null);

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();

    }

    private void init() {
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getMyFriends(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMyFriends(refreshLayout);
    }


    @Override
    public void refreshSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows) {
        if (refresh != null)
            refresh.finishRefresh();

        mAdapter.setList(myFollows);

        if (myFollows.size() <= 0) {
            mAdapter.setEmptyView(R.layout.buddy_empty);
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
