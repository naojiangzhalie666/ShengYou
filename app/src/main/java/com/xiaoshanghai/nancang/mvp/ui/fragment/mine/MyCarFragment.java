package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.CacheConstant;
import com.xiaoshanghai.nancang.mvp.contract.MyCarContract;
import com.xiaoshanghai.nancang.mvp.presenter.MyCarPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyCarAdapter;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.GridDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class MyCarFragment extends BaseMvpFragment<MyCarPresenter> implements MyCarContract.View, OnRefreshListener {

    @BindView(R.id.rcy_car)
    RecyclerView rcyCar;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private MyCarAdapter mAdapter;


    public static MyCarFragment newInstance() {
        return new MyCarFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_my_car;
    }

    @Override
    protected void onLazyLoad() {
        mAdapter = new MyCarAdapter();
        rcyCar.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcyCar.addItemDecoration(new GridDecoration(20, 10, 17, 17, 2));
        rcyCar.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Decks decks = mAdapter.getItem(position);

                boolean isExpired = decks.getValidity() == 0;      //是否过期
                boolean isUse = decks.getUsed() == 1;          //是否使用

                if (isExpired) {
                    //过期
                    //TODO 这里要做购买业务
                } else {
                    if (!isUse) {
                        //未使用
                        mPresenter.useCar(decks);
                    }
                }
            }
        });

        mPresenter.getMyCar(null);
    }

    @Override
    protected MyCarPresenter createPresenter() {
        return new MyCarPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        refresh.setOnRefreshListener(this);

    }

    @Override
    public void onCarSuccess(List<Decks> result) {
        if (result.size() > 0) {
            mAdapter.setList(result);
        } else {
            mAdapter.setEmptyView(R.layout.my_headwear_empty);
        }
    }

    @Override
    public void useCarSuccess(Decks decks, String data) {
        if (data.equals("1")) {
            if (CacheConstant.result != null) {
                CacheConstant.result.setCar(decks);
            }

            List<Decks> data1 = mAdapter.getData();
            for (Decks decks1 : data1) {
                if (decks1.getId().equals(decks.getId())){
                    decks1.setUsed(1);
                } else {
                    decks1.setUsed(0);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onCarError(String msg) {
        mAdapter.setEmptyView(R.layout.my_headwear_empty);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMyCar(refreshLayout);
    }
}
