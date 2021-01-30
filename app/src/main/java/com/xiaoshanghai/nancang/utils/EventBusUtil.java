package com.xiaoshanghai.nancang.utils;

import com.xiaoshanghai.nancang.net.bean.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lxy on 2019/11/8.
 */

public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}
