package com.xiaoshanghai.nancang.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoshanghai.nancang.net.bean.Event;
import com.xiaoshanghai.nancang.utils.EventBusUtil;
import com.xiaoshanghai.nancang.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public  abstract class BaseFragment  extends RxFragment {

    //当前类是否处理ui逻辑
    protected final int disableLayout = -10086;

    Unbinder bind;

    //    protected StatusLayoutManager statusLayoutManager;
    private boolean isInitView = false;
    private boolean isVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterEventBus() && (!EventBus.getDefault().isRegistered(this))) {
            EventBusUtil.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int setLayoutId = setLayoutId();
        if (disableLayout != setLayoutId) {
            StatusBarUtil.updateStatusWordColor(getActivity(), true);
            View view = inflater.inflate(setLayoutId, container, false);
            bind = ButterKnife.bind(this, view);
            initView(savedInstanceState);
            isInitView = true;
            isCanLoadData();
            return view;
        } else {
            isInitView = true;
            isCanLoadData();
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

    public abstract int setLayoutId();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
        }
    }

    public abstract void initView(Bundle savedInstanceState);

    private void isCanLoadData() {
        //所以条件是view初始化完成并且对用户可见
        if (isInitView && isVisible) {
            onLazyLoad();
            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 加载要显示的数据
     */
    protected void onLazyLoad() {

    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

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
    protected void receiveEvent(Event event) {}

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null)
            bind.unbind();

        if (isRegisterEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBusUtil.unregister(this);
        }
    }
}
