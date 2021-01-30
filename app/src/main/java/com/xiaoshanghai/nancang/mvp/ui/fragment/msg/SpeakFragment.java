package com.xiaoshanghai.nancang.mvp.ui.fragment.msg;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.mvp.contract.SpeakContract;
import com.xiaoshanghai.nancang.mvp.presenter.SpeakPresenter;
import com.xiaoshanghai.nancang.net.bean.SystemNotic;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;

import java.util.List;

import butterknife.BindView;

public class SpeakFragment extends BaseMvpFragment<SpeakPresenter> implements SpeakContract.View {

    @BindView(R.id.ll_view)
    LinearLayout llView;
    @BindView(R.id.conversation_layout)
    ConversationLayout mConversationLayout;

//    @BindView(R.id.conversation_title)
//    TextView mSystemTitle;
//    @BindView(R.id.conversation_time)
//    TextView mSystemTime;
//    @BindView(R.id.conversation_last_msg)
//    TextView mLastMsg;

    public static SpeakFragment newInstance() {
        return new SpeakFragment();
    }

    @Override
    protected SpeakPresenter createPresenter() {
        return new SpeakPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_speak;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mPresenter.initIM(mConversationLayout);
        mPresenter.getSystemNoic();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {// 重新显示到最前端中
            mPresenter.getSystemNoic();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getSystemNoic();
    }

    @Override
    public void onSystemNoicSuccess(List<SystemNotic> notics) {

//        if (notics != null && notics.size() > 0) {
//            SystemNotic systemNotic = notics.get(0);
//            mLastMsg.setText("["+systemNotic.getNotifyTitle()+"]");
//            Date date = DateUtil.stringToDate(systemNotic.getCreateTime(), DateUtil.DatePattern.ALL_TIME);
//            String timeFormatText = DateTimeUtil.getTimeFormatText(date);
//            mSystemTime.setText(timeFormatText);
//        }
    }

    @Override
    public void onError(String msg) {

    }

//    @OnClick({R.id.item_left})
//    public void onClick(View view){
//        ActStartUtils.startAct(getActivity(), SystemNoticAct.class);
//    }
}
