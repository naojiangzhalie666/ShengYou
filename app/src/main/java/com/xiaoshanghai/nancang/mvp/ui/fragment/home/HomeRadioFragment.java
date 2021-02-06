package com.xiaoshanghai.nancang.mvp.ui.fragment.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.helper.EnterRoomHelp;
import com.xiaoshanghai.nancang.mvp.contract.HomeRadioContract;
import com.xiaoshanghai.nancang.mvp.presenter.HomeRadioPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.EmptyView;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;
import com.xiaoshanghai.nancang.net.bean.RoomResult;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.view.HomeGridDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.trtc.TRTCCloudDef;

import java.util.List;

import butterknife.BindView;

public class HomeRadioFragment extends BaseMvpFragment<HomeRadioPresenter> implements HomeRadioContract.View, OnItemClickListener, OnRefreshLoadMoreListener {
    @BindView(R.id.rlView)
    RecyclerView rlView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    public HomeSortResult result;

    private HomeRadioAdapter mAdapter;

    private EmptyView emptyView;


    public static HomeRadioFragment newInstance(HomeSortResult bean) {
        HomeRadioFragment fragment = new HomeRadioFragment();
//        fragment.result = bean;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.HOME_RADIO_TYPE, bean);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_home_readio;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        emptyView = new EmptyView(getActivity());
        Bundle arguments = getArguments();
        result = (HomeSortResult) arguments.getSerializable(Constant.HOME_RADIO_TYPE);

        mPresenter.attachView(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void onLazyLoad() {
        mAdapter = new HomeRadioAdapter();
        rlView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        rlView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlView.addItemDecoration(new HomeGridDecoration(25,26,30,30,2));
        rlView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        refreshLayout.autoRefresh();


    }

    @Override
    protected HomeRadioPresenter createPresenter() {
        return new HomeRadioPresenter();
    }

    @Override
    public void refreshRoom(RefreshLayout refreshLayout, List<RoomResult> roomResults) {
        refreshLayout.finishRefresh();
        mAdapter.setList(roomResults);
        if (roomResults.size() <= 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
            emptyView.setEmptyImgAndEmptyText(R.drawable.img_empty,"");
            mAdapter.setEmptyView(emptyView);
        } else {
            refreshLayout.resetNoMoreData();
        }
    }

    @Override
    public void loadMoreRoom(RefreshLayout refreshLayout, List<RoomResult> roomResults) {
        mAdapter.addData(roomResults);

        if (roomResults.size() <= 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }

    }

    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {
        refreshLayout.finishLoadMoreWithNoMoreData();

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        RoomResult item = mAdapter.getItem(position);
//        EnterRoomHelp.enterRoom
        EnterRoomHelp.enterRoom(getActivity(),item.getRoomId(), SPUtils.getInstance().getUserInfo().getId(),
                TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC,item.getRoomName(),item.getRoomLock().equals("1"),item.getUserId(),item.getRoomPassword());
//        ActStartUtils.startAct(getActivity(), VoiceLiveRoomAct.class);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getRooms(refreshLayout, result != null ? result.getId() : "");

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getRooms(refreshLayout, result != null ? result.getId() : "");
    }


}
