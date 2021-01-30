package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.SplashContract;

public class SplashPresenter extends BasePresenter<SplashContract.View>  implements SplashContract.Presenter{

    public SplashPresenter() {

    }


    @Override
    public void startMainActivity() {
            getView().startMainActivity();
    }
}
