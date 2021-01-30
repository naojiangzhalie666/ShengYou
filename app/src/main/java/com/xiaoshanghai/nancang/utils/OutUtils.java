package com.xiaoshanghai.nancang.utils;

public class OutUtils {

    // 两次点击按钮之间的点击间隔不能少于1500毫秒
    private final static int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    private final static int MIN_NOTIFY_DELAY_TIME = 2000;
    private static long lastNotify;

    private final static int MIN_ANIMO_DELAY_TIME = 5000;
    private static long lastaNIMO;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = curClickTime;
            flag = true;
        }
        return flag;
    }

    public static boolean isNotify() {
        boolean flag = false;
        long currTime = System.currentTimeMillis();
        if ((currTime -lastNotify )>=MIN_NOTIFY_DELAY_TIME ){
            lastNotify = currTime;
            flag = true;
        }

        return flag;
    }

    public static boolean isAnimo(){

        boolean flag = false;
        long currTime = System.currentTimeMillis();
        if ((currTime -lastaNIMO )>=MIN_ANIMO_DELAY_TIME ){
            lastaNIMO = currTime;
            flag = true;
        }

        return flag;
    }
}
