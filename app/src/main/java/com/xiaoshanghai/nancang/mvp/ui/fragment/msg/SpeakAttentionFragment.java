package com.xiaoshanghai.nancang.mvp.ui.fragment.msg;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.AttentionContract;
import com.xiaoshanghai.nancang.mvp.presenter.AttentionPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.SpeakAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.SpeakAttentionAdapter;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SpeakAttentionFragment extends BaseMvpFragment<AttentionPresenter> implements AttentionContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rcy_attention)
    RecyclerView rcyAttention;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private SpeakAttentionAdapter mAdapter;

    public static SpeakAttentionFragment newInstance() {
        return new SpeakAttentionFragment();
    }

    @Override
    protected AttentionPresenter createPresenter() {
        return new AttentionPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my_attention;
    }

    @Override
    protected void onLazyLoad() {
        init();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);

    }

    private void init() {
        refresh.setOnRefreshLoadMoreListener(this);


        mAdapter = new SpeakAttentionAdapter();
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

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.USER_ID, mAdapter.getItem(position).getUserId());
//                ActStartUtils.startAct(SpeakAttentionFragment.this.getActivity(), HomePageAct.class, bundle);

                MyFollowResult item = mAdapter.getItem(position);

                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(V2TIMConversation.V2TIM_C2C);
                chatInfo.setId(item.getUserId());
                chatInfo.setChatName(item.getUserName());
                List<Object> list = new ArrayList<>();
                list.add(item.getUserPictureUrl());
                chatInfo.setIconUrlList(list);

                Intent intent = new Intent(getActivity(), SpeakAct.class);
                intent.putExtra(Constant.CHAT_INFO, chatInfo);
                getActivity().startActivity(intent);
            }
        });


        mPresenter.refreshFollow(null);


    }


    @Override
    public void refreshSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows) {
        if (refresh != null)
            refresh.finishRefresh();

        mAdapter.setList(myFollows);

        if (myFollows.size() <= 0) {
            mAdapter.setEmptyView(R.layout.attention_empty);
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


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.refreshFollow(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.refreshFollow(refreshLayout);
    }
}
