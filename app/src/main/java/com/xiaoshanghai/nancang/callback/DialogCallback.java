package com.xiaoshanghai.nancang.callback;

import android.app.Dialog;

import com.xiaoshanghai.nancang.view.TipsDialog;

/**
 * 文件名:DialogCallback
 * 创建者:zed
 * 创建日期:2019/4/25 19:03
 * 描述:TODO
 */
public interface DialogCallback {
    boolean callback(TipsDialog tipsDialog, Dialog dialog);
}
