package com.xiaoshanghai.nancang.mvp.ui.activity.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.mvp.contract.CashRecordContract;
import com.xiaoshanghai.nancang.mvp.presenter.CashRecordPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.CashRecordAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.CashBean;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.CashMuListEntity;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.DateUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CashRecordAct extends BaseMvpActivity<CashRecordPresenter> implements CashRecordContract.View, OnRefreshLoadMoreListener, TitleBarClickCallback {


    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_date)
    TextView mTvDate;
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
    @BindView(R.id.iv_to_top)
    ImageView ivToTop;

    private String mUserId;

    private ArrayList<CashBean> list = new ArrayList<>();

    private ArrayList<CashMuListEntity> header = new ArrayList<>();

    private CashRecordAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_cash_record;
    }


    @Override
    protected CashRecordPresenter createPresenter() {
        return new CashRecordPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        list.clear();
        header.clear();
        mPresenter.getIncomeList(null,mUserId, mTvDate.getText().toString().trim());
    }

    private void init() {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
        refresh.setOnRefreshLoadMoreListener(this);
        UserBean userInfo = SPUtils.getInstance().getUserInfo();
        mUserId = userInfo.getId();

        Date date = new Date();
        String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
        mTvDate.setText(dateToString);

        mAdapter = new CashRecordAdapter(header);
        rycRecord.setLayoutManager(new LinearLayoutManager(this));
        rycRecord.setAdapter(mAdapter);
    }



    @Override
    public void setTime(String date) {
        mTvDate.setText(date);
        mPresenter.mPage = mPresenter.initPage;
        list.clear();
        header.clear();
        mPresenter.getIncomeList(null, mUserId,mTvDate.getText().toString().trim());
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        mPresenter.mPage = mPresenter.initPage;
        list.clear();
        header.clear();
        mPresenter.getIncomeList(refreshLayout,  mUserId,mTvDate.getText().toString().trim());

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getIncomeList(refreshLayout, mUserId, mTvDate.getText().toString().trim());
    }


    @Override
    public void refreshSuccess(RefreshLayout refresh, List<CashEntity> incomeList) {
        if (refresh != null) {
            refresh.finishRefresh();
        }
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
    public void loadMoreSuccess(RefreshLayout refresh, List<CashEntity> incomeList) {
        if (incomeList.size() > 0) {
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

    private void initHeader() {
        header.clear();
        for (CashBean incomeBean : list) {
            CashMuListEntity headerBean = new CashMuListEntity(true, incomeBean.getDate(), null);
            header.add(headerBean);

            List<CashEntity> incomeResults = incomeBean.getCashResult();
            for (CashEntity incomeResult : incomeResults) {
                if (incomeBean != null) {
                    CashMuListEntity bean = new CashMuListEntity(false, null, incomeResult);
                    header.add(bean);
                }
            }
        }
    }

    private void tissue(List<CashEntity> incomeList) {

        for (int i = 0; i < incomeList.size(); i++) {
            boolean isSelect = false;
            for (int j = 0; j < list.size(); j++) {
                if (incomeList.get(i).getDate().equals(list.get(j).getDate())) {

                    list.get(j).getCashResult().add(incomeList.get(i));

                    isSelect = true;
                    break;
                }
            }

            if (!isSelect) {
                String date = incomeList.get(i).getDate();
                ArrayList<CashEntity> results = new ArrayList<>();
                results.add(incomeList.get(i));

                CashBean bean = new CashBean(date, results);
                list.add(bean);
            }
        }
    }


    @Override
    public void onError(String msg) {
        ToastUtils.showLong(msg);
    }

    @OnClick({R.id.iv_calendar, R.id.iv_now_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_calendar:
                mPresenter.selectTime(mTvDate.getText().toString().trim());
                break;
            case R.id.iv_now_day:
                Date date = new Date();
                String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
                mTvDate.setText(dateToString);
                list.clear();
                header.clear();
                mPresenter.getIncomeList(null, mUserId, mTvDate.getText().toString().trim());
                break;
        }
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        ActStartUtils.startAct(this, MyGoldAct.class);
    }
}
