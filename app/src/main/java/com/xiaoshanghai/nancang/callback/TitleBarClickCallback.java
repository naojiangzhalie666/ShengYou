package com.xiaoshanghai.nancang.callback;

public interface TitleBarClickCallback {
    /**
     * titleBar左边点击回调
     */
    void titleLeftClick() ;

    /**
     * titleBar右边点击回调
     * @param status 点击r1 或是 r2
     */
    void titleRightClick(int status);
}
