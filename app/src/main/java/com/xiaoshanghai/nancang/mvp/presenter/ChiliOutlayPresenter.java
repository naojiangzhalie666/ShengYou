package com.xiaoshanghai.nancang.mvp.presenter;

import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.ChiliOutlayContract;
import com.xiaoshanghai.nancang.mvp.model.ChiliOutlayModel;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.ChiliOutlayFragment;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.ChiliIncomeResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.utils.DateUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiliOutlayPresenter extends BasePresenter<ChiliOutlayContract.View> implements ChiliOutlayContract.Presenter {


    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    private ChiliOutlayModel model;

    public ChiliOutlayPresenter() {
        model = new ChiliOutlayModel();
    }

    @Override
    public void selectTime(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.stringToDate(date, DateUtil.DatePattern.ONLY_DAY));

        TimePickerView pvTime = new TimePickerBuilder(((ChiliOutlayFragment)getView()).getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
                getView().setTime(dateToString);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("日期选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#000000"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#000000"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(calendar)// 如果不设置的话，默认是系统时间*/
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setDecorView(((ChiliOutlayFragment) (getView())).getActivity().getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvTime.show();
    }

    @Override
    public void getOutlayList(RefreshLayout refresh, String date) {
        String replace = date.replace("-", "/");
        model.getOutlayList(mPage + "", size + "",replace)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<ChiliIncomeResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<ChiliIncomeResult>> bean, BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>> response) {
                        List<ChiliIncomeResult> records = bean.getRecords();
//                        if (records != null) {
                        if (mPage == initPage) {
                            getView().refreshSuccess(refresh, records);
                        } else {
                            getView().loadMoreSuccess(refresh, records);
                        }
                        if (records.size() > 0)
                            mPage++;
//                        }
                    }

                    @Override
                    protected void error(String msg) {
                        if (refresh != null) {
                            refresh.finishRefresh();
                            refresh.finishLoadMore();
                        }
                        getView().onError(msg);
                    }
                });
    }

}
