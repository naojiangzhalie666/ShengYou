package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.MaterialContract;
import com.xiaoshanghai.nancang.mvp.model.MaterialModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;

public class MaterialPresenter extends BasePresenter<MaterialContract.View> implements MaterialContract.Presenter {

    private MaterialModel model;

    public MaterialPresenter() {
        model = new MaterialModel();
    }

    @Override
    public void getMyFamily(String userId) {
        model.getFamilyInfo(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MyFamilyResult>() {
                    @Override
                    protected void success(MyFamilyResult bean, BaseResponse<MyFamilyResult> response) {
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
