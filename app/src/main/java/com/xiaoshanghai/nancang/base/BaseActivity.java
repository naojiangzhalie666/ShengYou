package com.xiaoshanghai.nancang.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.xiaoshanghai.nancang.net.bean.Event;
import com.xiaoshanghai.nancang.utils.AppActivityStatusUtil;
import com.xiaoshanghai.nancang.utils.EventBusUtil;
import com.xiaoshanghai.nancang.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

public abstract class BaseActivity extends RxAppCompatActivity implements IActivity {

    //当前类是否处理ui逻辑
    protected final int disableLayout = -10086;
    Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppActivityStatusUtil.register(this);
        if (isFull()) {
            StatusBarUtil.setTransparentForImageView(this, null);
            StatusBarUtil.updateStatusWordColor(this, true);
        }
        //如果 id==disableLayout 则不需要 处理当前逻辑 交给子类处理
        int layoutId = setLayoutId();
        if (disableLayout != layoutId) {
            setContentView(layoutId);
            bind = ButterKnife.bind(this);
            initView(savedInstanceState);
        }

        if (isRegisterEventBus() && (!EventBus.getDefault().isRegistered(this))) {
            EventBusUtil.register(this);
        }


    }

    @Override
    public boolean isFull() {
        return true;
    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    /**
     * EventBus 接收普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    /**
     * EventBus接收粘性事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    /**
     * 每次启动activity都会调用此方法,此处主要为了解决快速点击跳转Activity重复打开的问题
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (checkDoubleClick(intent)) {
            super.startActivityForResult(intent, requestCode, options);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bind != null)
            bind.unbind();

        AppActivityStatusUtil.unRegister(this);

        if (isRegisterEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBusUtil.unregister(this);
        }

    }




    private String mActivityJumpTag;        //activity跳转tag
    private long mClickTime;                //activity跳转时间

    /**
     * 检查是否重复跳转，不需要则重写方法并返回true
     */
    protected boolean checkDoubleClick(Intent intent) {

        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        }else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        }else {
            return true;
        }

        if (tag.equals(mActivityJumpTag) && mClickTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mClickTime = SystemClock.uptimeMillis();
        return result;
    }
}
