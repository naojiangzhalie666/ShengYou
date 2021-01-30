package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.CacheConstant;
import com.xiaoshanghai.nancang.mvp.contract.MyHeadwearContract;
import com.xiaoshanghai.nancang.mvp.presenter.MyHeadwearPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyHeadwearAdapter;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class MyHeadwearFragment extends BaseMvpFragment<MyHeadwearPresenter> implements MyHeadwearContract.View, OnRefreshListener {

    @BindView(R.id.rcy_headwear)
    RecyclerView rcyHeadwear;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;


    private MyHeadwearAdapter mAdapter;

    public static MyHeadwearFragment newInstance() {
        return new MyHeadwearFragment();
    }

    @Override
    protected MyHeadwearPresenter createPresenter() {
        return new MyHeadwearPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my_headwear;
    }

    @Override
    protected void onLazyLoad() {
        mAdapter = new MyHeadwearAdapter();
        rcyHeadwear.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcyHeadwear.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Decks decks = mAdapter.getItem(position);

                boolean isExpired = decks.getValidity() == 0;      //是否过期
                boolean isUse = decks.getUsed() == 1;          //是否使用

                if (isExpired) {

                    if (decks == null) return;
                    TipsDialog.createDialog(getActivity(), R.layout.dialog_headwear_store)
                            .setText(R.id.tv_title, "续费提示")
                            .setText(R.id.tv_gift_info, "续费 " + decks.getDeckName() + "(" + decks.getUseDay() + "天)")
                            .setText(R.id.tv_price, decks.getCostNumber() + (decks.getCostType() == 1 ? "金币" : decks.getCostType() == 2 ? "辣椒" : ""))
                            .bindClick(R.id.tv_cancel, (v, dialog) -> {
                                dialog.dismiss();
                            })
                            .bindClick(R.id.tv_comit, (v, dialog) -> {

                                mPresenter.buyDeck(decks.getId());

                            })
                            .show();


                } else {

                    if (!isUse) {
                        mPresenter.useHeadwear(decks);

                    }
                }

            }
        });

        mPresenter.getMyHeadwear(null);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        refresh.setOnRefreshListener(this);

    }

    @Override
    public void onSuccess(DeckResult result, RefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        if (result != null) {
            if (result.getDecks().size() > 0) {
                mAdapter.setList(result.getDecks());
            } else {
                mAdapter.setEmptyView(R.layout.my_headwear_empty);
            }
        }
    }

    @Override
    public void useHeadwearSuccess(Decks decks, String data) {
        if (data.equals("1")) {
            if (CacheConstant.result != null) {
                CacheConstant.result.setHeadwear(decks);
            }

            List<Decks> data1 = mAdapter.getData();
            for (Decks decks1 : data1) {
                if (decks1.getId().equals(decks.getId())) {
                    decks1.setUsed(1);
                } else {
                    decks1.setUsed(0);
                }
            }

            mAdapter.notifyDataSetChanged();

//            mPresenter.getMyHeadwear(null);
        }
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void myHeadwearError(String mgs) {
        ToastUtils.showShort(mgs);
        mAdapter.setEmptyView(R.layout.my_headwear_empty);
    }

    @Override
    public void buyDeckSuccess(Integer status) {
        if (status == 1) {
            ToastUtils.showShort("续费成功");
            mPresenter.getMyHeadwear(null);
        } else {
            ToastUtils.showShort("余额不足");
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMyHeadwear(refreshLayout);
    }
}
