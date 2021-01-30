package com.xiaoshanghai.nancang.base;

import android.os.Bundle;

import com.xiaoshanghai.nancang.view.LoadingDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.annotations.Nullable;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);

    }

    /**
     * 创建Presenter
     *
     * @return presenter
     */
    protected abstract P createPresenter();

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(true);
        }
        loadingDialog.show();
        loadingDialog.startRotate();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
//            loadingDialog.stopRotate();
            loadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();

    }

    @Override
    public <B> LifecycleTransformer<B> getActLifeRecycle() {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }

}
