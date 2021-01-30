package com.xiaoshanghai.nancang.base;

import android.app.Activity;
import android.os.Bundle;

import io.reactivex.annotations.Nullable;

public interface IActivity {

    /**
     * 初始化 View, 如果 {@link #setLayoutId()} 返回 0, 则不会调用 {@link Activity#setContentView(int)}
     *
     */
    int setLayoutId();

    /**
     * 初始化数据
     *
     *
     */
    void initView(@Nullable Bundle savedInstanceState);


    /**
     * 是否使用 EventBus
     *
     * @return
     */
    boolean isRegisterEventBus();

    /**
     * 是否全屏
     * @return
     */
    boolean isFull();

}
