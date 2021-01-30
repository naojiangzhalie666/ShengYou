package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;

public interface SplashContract {

    interface View extends BaseView {
        void startMainActivity();

    }

    interface Presenter {
        void startMainActivity();
    }

    interface Model {

    }


}
