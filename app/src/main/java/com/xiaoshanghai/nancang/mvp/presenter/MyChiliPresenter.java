package com.xiaoshanghai.nancang.mvp.presenter;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MyChiliContract;
import com.xiaoshanghai.nancang.mvp.model.MyChiliModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyChiliAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.ChiliIncomeFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.ChiliOutlayFragment;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class MyChiliPresenter extends BasePresenter<MyChiliContract.View> implements MyChiliContract.Presenter {

    private MyChiliModel model;

    public MyChiliPresenter() {
        model = new MyChiliModel();
    }

    @Override
    public void getMyChili() {
        model.getMyChili()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        getView().onChiliSuccess(bean + "");
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);

                    }
                });
    }

    @Override
    public void initFragments(ViewPager mViewPager, TextView one, TextView two) {

        ChiliIncomeFragment incomeFragment = new ChiliIncomeFragment();

        ChiliOutlayFragment outlayFragment = new ChiliOutlayFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(incomeFragment);
        fragments.add(outlayFragment);

        HomeRadioPageAdapter fragmentAdapter = new HomeRadioPageAdapter(((MyChiliAct) getView()).getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    one.setSelected(true);
                    two.setSelected(false);
                } else if (position == 1) {
                    one.setSelected(false);
                    two.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
