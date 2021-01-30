package com.xiaoshanghai.nancang.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * create presenter
     * @return presenter
     */
    protected abstract P createPresenter();

    @Override
    public <B> LifecycleTransformer<B> getActLifeRecycle() {
        return this.bindUntilEvent(FragmentEvent.DESTROY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }



}
