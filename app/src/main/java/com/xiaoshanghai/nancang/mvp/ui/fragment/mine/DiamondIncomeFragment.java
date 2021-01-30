package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.mvp.contract.DiamondIncomeContract;
import com.xiaoshanghai.nancang.mvp.presenter.DiamondIncomePresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.IncomeAdapter;
import com.xiaoshanghai.nancang.net.bean.IncomeBean;
import com.xiaoshanghai.nancang.net.bean.IncomeHeaderBean;
import com.xiaoshanghai.nancang.net.bean.IncomeResult;
import com.xiaoshanghai.nancang.utils.DateUtil;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 钻石收入记录
 */
public class DiamondIncomeFragment extends BaseMvpFragment<DiamondIncomePresenter> implements DiamondIncomeContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_calendar)
    ImageView ivCalendar;
    @BindView(R.id.iv_now_day)
    ImageView ivNowDay;
    @BindView(R.id.cv_date_info)
    CardView cvDateInfo;
    @BindView(R.id.ryc_record)
    RecyclerView rycRecord;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private ArrayList<IncomeBean> list = new ArrayList<>();

    private ArrayList<IncomeHeaderBean> header = new ArrayList<>();

    private IncomeAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_diamond_income;
    }

    @Override
    protected DiamondIncomePresenter createPresenter() {
        return new DiamondIncomePresenter();
    }


    private void init() {
        mPresenter.attachView(this);
        refresh.setOnRefreshLoadMoreListener(this);

        Date date = new Date();
        String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
        tvDate.setText(dateToString);

        mAdapter = new IncomeAdapter(header);
        rycRecord.setLayoutManager(new LinearLayoutManager(getActivity()));
        rycRecord.setAdapter(mAdapter);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        mPresenter.getIncomeList(null, tvDate.getText().toString().trim());
    }


    @OnClick({R.id.iv_calendar, R.id.iv_now_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_calendar:
                mPresenter.selectTime(tvDate.getText().toString().trim());
                break;
            case R.id.iv_now_day:
                Date date = new Date();
                String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
                tvDate.setText(dateToString);
                mPresenter.mPage = mPresenter.initPage;
                mPresenter.getIncomeList(null, tvDate.getText().toString().trim());
                break;
        }
    }


    @Override
    public void setTime(String date) {
        tvDate.setText(date);
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getIncomeList(null, tvDate.getText().toString().trim());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getIncomeList(refreshLayout, tvDate.getText().toString().trim());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getIncomeList(refreshLayout, tvDate.getText().toString().trim());
    }

    @Override
    public void refreshSuccess(RefreshLayout refresh, List<IncomeResult> incomeList) {
        if (refresh != null) {
            refresh.finishRefresh();
        }

        list.clear();
        header.clear();

        tissue(incomeList);

        initHeader();

        if (header.size() <= 0) {
            mAdapter.setEmptyView(R.layout.expenses_empty);
        }
        mAdapter.notifyDataSetChanged();

        if (incomeList.size() <= 0) {
            if (refresh != null)
                refresh.finishLoadMoreWithNoMoreData();
        } else {
            if (refresh != null)
                refresh.resetNoMoreData();
        }


    }

    @Override
    public void loadMoreSuccess(RefreshLayout refresh, List<IncomeResult> incomeList) {

        if (incomeList.size()>0) {
            if (refresh != null) {
                refresh.finishLoadMore();
            }
        } else {
            if (refresh != null) {
                refresh.finishLoadMoreWithNoMoreData();
            }
        }

        tissue(incomeList);

        initHeader();

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    private void initHeader() {
        header.clear();
        for (IncomeBean incomeBean : list) {
            IncomeHeaderBean headerBean = new IncomeHeaderBean(true, incomeBean.getDate(), null);
            header.add(headerBean);
            List<IncomeResult> incomeResults = incomeBean.getIncomeResults();
            for (IncomeResult incomeResult : incomeResults) {
                IncomeHeaderBean bean = new IncomeHeaderBean(false, null, incomeResult);
                header.add(bean);
            }
        }
    }

    private void tissue(List<IncomeResult> incomeList) {

        for (int i = 0; i < incomeList.size(); i++) {
            boolean isSelect = false;
            for (int j = 0; j < list.size(); j++) {
                if (incomeList.get(i).getDate().equals(list.get(j).getDate())) {
                    list.get(j).getIncomeResults().add(incomeList.get(i));
                    isSelect = true;
                    break;
                }
            }

            if (!isSelect) {
                IncomeBean bean = new IncomeBean();
                bean.setDate(incomeList.get(i).getDate());
                ArrayList<IncomeResult> results = new ArrayList<>();
                results.add(incomeList.get(i));
                bean.setIncomeResults(results);
                list.add(bean);
            }
        }
    }
}
