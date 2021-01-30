package com.xiaoshanghai.nancang.mvp.presenter;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.NobleWebContract;
import com.xiaoshanghai.nancang.mvp.model.NobleWebModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.NobleResult;

import java.util.List;

public class NobleWebPresenter extends BasePresenter<NobleWebContract.View> implements NobleWebContract.Presenter {

    private NobleWebModel model;

    public NobleWebPresenter() {
        model = new NobleWebModel();
    }

    @Override
    public void initWebView(WebView webView) {
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSetting = webView.getSettings();
//        webSetting.setUserAgentString(USER_AGENT + webSetting.getUserAgentString());

        webSetting.setDatabaseEnabled(true);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setTextSize(WebSettings.TextSize.NORMAL);

        // ===设置JS可用
        webSetting.setJavaScriptEnabled(true);
        // JS打开窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // ===设置JS可用
        // 可以访问文件
        webSetting.setAllowFileAccess(true);
        // ===缩放可用
        webSetting.setSupportZoom(false);
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); //设置缩放功能   //能不能缩放 取决于网页设置
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBuiltInZoomControls(true);
        // ===缩放可用
        // 支持多窗口
        webSetting.setSupportMultipleWindows(true);
        // ===============缓存
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);// 决定是否从网络上取数据。
        webSetting.setAppCacheEnabled(true);
        // ===============缓存
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // ==定位
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        // ==定位
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 适配图片加载不出来的问题
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void getNobleList() {
        model.getNobleList()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<NobleResult>>() {
                    @Override
                    protected void success(List<NobleResult> bean, BaseResponse<List<NobleResult>> response) {
                        getView().nobleSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);

                    }
                });
    }
}
