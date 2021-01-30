package com.xiaoshanghai.nancang.mvp.ui.fragment.square;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.callback.HomeSortCallback;
import com.xiaoshanghai.nancang.mvp.contract.SquareConstract;
import com.xiaoshanghai.nancang.mvp.presenter.SquarePresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.TopicNotificAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.SquareIndexAdapter;
import com.xiaoshanghai.nancang.net.bean.TopicMsg;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.ScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SquareFragment extends BaseMvpFragment<SquarePresenter> implements SquareConstract.View {


    @BindView(R.id.magicindicator)
    MagicIndicator magicindicator;
    @BindView(R.id.s_view_pager)
    ScrollViewPager sViewPager;
    @BindView(R.id.iv_tz)
    ImageView ivTz;
    @BindView(R.id.tv_notice_num)
    TextView tvNoticeNum;


    @Override
    protected SquarePresenter createPresenter() {
        return new SquarePresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        SquareV1Fragment squareV1Fragment1 = new SquareV1Fragment();
        SquareV1Fragment squareV1Fragment2 = new SquareV1Fragment();
        HallFragment hallFragment = new HallFragment();
        FamilyFragment familyFragment = new FamilyFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(squareV1Fragment1);
        fragments.add(squareV1Fragment2);
        fragments.add(hallFragment);
//        fragments.add(familyFragment);

        FragmentManager supportFragmentManager = getChildFragmentManager();

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(supportFragmentManager, fragments);

        sViewPager.setAdapter(adapter);
        sViewPager.setOffscreenPageLimit(3);

        List<String> tables = new ArrayList<>();
        tables.add("附近");
        tables.add("推荐");
        tables.add("大厅");
//        tables.add("家族");


        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new SquareIndexAdapter(tables, magicindicator, (HomeSortCallback<String>) (result, index) -> sViewPager.setCurrentItem(index)));

        magicindicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicindicator, sViewPager);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {// 重新显示到最前端中
            mPresenter.getTopicMsg(SPUtils.getInstance().getUserInfo().getId());
        }
    }

    @Override
    public void onTopicMsgSuccess(List<TopicMsg> topicMsgs) {
        if (topicMsgs == null || topicMsgs.size() <= 0) {
            tvNoticeNum.setVisibility(View.GONE);
        } else {
            TopicMsg topicMsg = topicMsgs.get(0);
            if (topicMsg == null) {
                tvNoticeNum.setVisibility(View.GONE);
            } else {
                Integer unReadCount = topicMsg.getUnReadCount();
                if (unReadCount == null || unReadCount <= 0) {
                    tvNoticeNum.setVisibility(View.GONE);
                } else {
                    tvNoticeNum.setVisibility(View.VISIBLE);
                    if (unReadCount > 99) {
                        tvNoticeNum.setText("99+");
                    } else {
                        tvNoticeNum.setText(unReadCount + "");
                    }
                }
            }
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick({R.id.ct_tz})
    public void onClick(View v) {
        ActStartUtils.startAct(getContext(), TopicNotificAct.class);
    }
}
