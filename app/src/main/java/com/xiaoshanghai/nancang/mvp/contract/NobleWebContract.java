package com.xiaoshanghai.nancang.mvp.contract;

import android.webkit.WebView;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.NobleResult;

import java.util.List;

public interface NobleWebContract {

    interface View extends BaseView {

        void nobleSuccess(List<NobleResult> results);

        void onError(String msg);

    }

    interface Presenter {

        void initWebView(WebView webView);

        void getNobleList();

    }

    interface Model {

        HttpObservable<BaseResponse<List<NobleResult>>> getNobleList();

    }


}
