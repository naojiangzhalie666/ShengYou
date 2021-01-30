package com.xiaoshanghai.nancang.mvp.presenter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MyDiamondContract;
import com.xiaoshanghai.nancang.mvp.model.MyDiamondModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyDiamondAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MyDiamondPresenter extends BasePresenter<MyDiamondContract.View> implements MyDiamondContract.Presenter {

    private MyDiamondModel model;

    private BaseMvpFragment baseFragment;

    public MyDiamondPresenter() {
        model = new MyDiamondModel();
    }

    /**
     * 替换页面
     *
     * @param fragment
     */
    @Override
    public void replace(BaseMvpFragment fragment) {
        if (fragment == baseFragment) return;
        FragmentManager supportFragmentManager = ((MyDiamondAct)getView()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            if (baseFragment == null) {
                fragmentTransaction.add(R.id.fl_fragment, fragment).show(fragment);
            } else if (baseFragment != fragment) {
                fragmentTransaction.add(R.id.fl_fragment, fragment).hide(baseFragment).show(fragment);
            }
        } else {
            fragmentTransaction.hide(baseFragment).show(fragment);
        }
        baseFragment = fragment;
        fragmentTransaction.commit();
    }

    @Override
    public void getMyDiamondNum() {
        model.getMyDiamondNum()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        getView().onDiamondSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
