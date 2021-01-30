package com.xiaoshanghai.nancang.mvp.contract;

import android.webkit.WebView;
import android.widget.TextView;

import com.xiaoshanghai.nancang.base.BaseView;

public interface WebContract {

    interface View extends BaseView {

        void onError(String msg);

    }

    interface Presenter {

        void initWebView(WebView webView, TextView textView);


    }

    interface Model {

    }


}
