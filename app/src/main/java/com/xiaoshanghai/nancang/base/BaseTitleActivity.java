package com.xiaoshanghai.nancang.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.xiaoshanghai.nancang.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;

public abstract class BaseTitleActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    @Nullable
    @BindView(R.id.btLeft)
    TextView btLeft;
    @Nullable
    @BindView(R.id.btRight)
    TextView btRight;
    @Nullable
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;
    @BindView(R.id.fake_title)
    View fakeTitle;
    //是否是刘海屏
    private boolean isNotchScreen = false;

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        //容器
        ViewGroup contentView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        setContentView(contentView);
        //添加新主体容器
        View childView = LayoutInflater.from(this).inflate(setBodyId(), contentView, false);
        contentView.addView(childView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childView.getLayoutParams();
        layoutParams.weight = 1;
        //绑定ui
        bind = ButterKnife.bind(this, contentView);
        notchScreen();
        initView(savedInstanceState);
    }

    //自己实现父类方法，不需要父类处理ui
    @Override
    public final int setLayoutId() {
        return disableLayout;
    }

    @OnClick(R.id.btLeft)
    protected void leftClick(View view) {
        //组件默认状态为INVISIBLE 当设置了按钮后状态更新为VISIBLE
        //表示可以操作组件 默认为关闭
        if (view.getVisibility() == View.VISIBLE)
            finish();
    }

    @OnClick(R.id.btRight)
    protected void rightClick(View view) {
    }

    /**
     * 创建Presenter
     *
     * @return presenter
     */
    protected abstract P createPresenter();

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    /**
     * 设置左标题
     *
     * @param word 文字
     */
    protected void setTitleLeft(String word) {
        if (btLeft != null) {
            btLeft.setVisibility(View.VISIBLE);
            btLeft.setText(word);
        }
    }

    /**
     * 设置左标题
     *
     * @param word  文字
     * @param resId 资源
     */
    protected void setTitleLeft(String word, int resId) {
        if (btLeft != null) {
            btLeft.setVisibility(View.VISIBLE);
            btLeft.setText(word);
            btLeft.setCompoundDrawablesRelativeWithIntrinsicBounds
                    (resId == -1 ? null : ContextCompat.getDrawable(this, resId),
                            null, null, null);
        }
    }

    /**
     * 设置标题
     *
     * @param word 文字
     */
    protected void setTvTitle(String word) {
        if (tvTitle != null) {
            tvTitle.setText(word);
        }
    }

    /**
     * 设置标题颜色
     *
     * @param color 颜色
     */
    protected void setTvTitleColor(int color) {
        if (tvTitle != null) {
            tvTitle.setTextColor(color);
        }
    }

    /**
     * 设置statusbar颜色
     *
     * @param color 颜色
     */
    protected void setStatusbarColor(int color) {
        if (mFakeStatusBar != null) {
            mFakeStatusBar.setBackgroundColor(color);
        }
    }

    /**
     * 设置标题栏颜色
     *
     * @param color 颜色
     */
    protected void setTitleBarColor(int color) {
        if (fakeTitle != null) {
            fakeTitle.setBackgroundColor(color);
        }
    }

    /**
     * 设置右标题
     *
     * @param word 文字
     */
    protected void setTitleRight(String word) {
        if (btRight != null) {
            btRight.setVisibility(TextUtils.isEmpty(word) ? View.INVISIBLE : View.VISIBLE);
            btRight.setText(word);
        }
    }

    /**
     * 设置右标题
     *
     * @param color 颜色
     */
    protected void setTitleRightColor(int color) {
        if (btRight != null) {
            btRight.setTextColor(color);
        }
    }

    /**
     * 设置右标题
     *
     * @param word  文字
     * @param resId 资源
     */
    protected void setTitleRight(String word, int resId) {
        if (btRight != null) {
            btRight.setVisibility(View.VISIBLE);
            btRight.setText(word);
            btRight.setCompoundDrawablesRelativeWithIntrinsicBounds
                    (null,
                            null,
                            resId == -1 ? null : ContextCompat.getDrawable(this, resId),
                            null);
        }
    }

    private void notchScreen() {
        mFakeStatusBar.post(new Runnable() {
            @Override
            public void run() {
                isNotchScreen = ImmersionBar.hasNotchScreen(BaseTitleActivity.this);
                if (isNotchScreen) {
                    int notchHeight = ImmersionBar.getNotchHeight(BaseTitleActivity.this);
                    if (notchHeight > mFakeStatusBar.getHeight()) {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mFakeStatusBar.getLayoutParams();
                        layoutParams.height = notchHeight;
                        mFakeStatusBar.setLayoutParams(layoutParams);
                    }
                }
                mFakeStatusBar.removeCallbacks(this);
            }
        });
    }

    protected boolean isNotchScreen() {
        return isNotchScreen;
    }

    public abstract int setBodyId();
}
