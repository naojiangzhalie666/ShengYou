package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.CarContract;
import com.xiaoshanghai.nancang.mvp.presenter.CarPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyCarRecordAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.CarEmptyView;
import com.xiaoshanghai.nancang.mvp.ui.view.CarRecordFoot;
import com.xiaoshanghai.nancang.mvp.ui.view.MyCarHeaderView;
import com.xiaoshanghai.nancang.net.bean.DeckResult;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.HeardGridDecoration;

import java.util.List;

import butterknife.BindView;

public class CarFragment extends BaseMvpFragment<CarPresenter> implements CarContract.View {

    @BindView(R.id.rcy_view)
    RecyclerView mRecyclerView;

    private MineReslut mResult;
    private boolean isSelf;
    private MyCarRecordAdapter mAdapter;
    private CarRecordFoot mFoot;
    private CarEmptyView empty;
    private MyCarHeaderView headerView;

    public static CarFragment newInstance() {
        return new CarFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected CarPresenter createPresenter() {
        return new CarPresenter();
    }

    private void initData() {
        Bundle arguments = getArguments();
        mResult = (MineReslut) arguments.getSerializable(Constant.MINE_RESLUT);
        if (mResult.getId().equals(SPUtils.getInstance().getUserInfo().getId())) {
            isSelf = true;
        } else {
            isSelf = false;
        }

        mFoot = new CarRecordFoot(getActivity());
        mFoot.setMineResult(mResult);

        empty = new CarEmptyView(getActivity());
        empty.setMineResult(mResult);


        mAdapter = new MyCarRecordAdapter();

        headerView = new MyCarHeaderView(getActivity());

        mAdapter.addHeaderView(headerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new HeardGridDecoration(20, 20, 17, 17, 2));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        initData();

        mPresenter.carRecord(mResult.getId());

    }

    @Override
    public void carSuccess(DeckResult deckResult) {

        if (deckResult != null) {
            headerView.setCarNum(deckResult.getDeckTotal());
            List<Decks> decks = deckResult.getDecks();

            if (decks.size() > 0) {
                mAdapter.setList(decks);
            } else {
                mAdapter.setList(null);
                mAdapter.setEmptyView(empty);
            }


        } else {
            mAdapter.setList(null);
            mAdapter.setEmptyView(empty);
        }

    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}
