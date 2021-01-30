package com.xiaoshanghai.nancang.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseView {
    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();


    /**
     * 绑定生命周期，防止内存泄露
     * @param <B>
     * @return
     */
    <B> LifecycleTransformer<B> getActLifeRecycle();

}
