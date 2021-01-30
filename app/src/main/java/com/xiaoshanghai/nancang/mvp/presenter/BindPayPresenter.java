package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.BindPayContract;
import com.xiaoshanghai.nancang.mvp.model.BindPayModel;

public class BindPayPresenter extends BasePresenter<BindPayContract.View>implements BindPayContract.Presenter {

    private BindPayModel model = new BindPayModel();

}
