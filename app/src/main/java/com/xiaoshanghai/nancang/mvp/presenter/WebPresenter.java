package com.xiaoshanghai.nancang.mvp.presenter;

import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.WebContract;
import com.xiaoshanghai.nancang.mvp.model.WebModel;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.SPUtils;

import java.util.HashMap;

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {

    private WebModel model;

    public WebPresenter() {
        model = new WebModel();
    }

    @Override
    public void initWebView(WebView webView, TextView textView) {
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
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                UserBean userInfo = SPUtils.getInstance().getUserInfo();
                if (userInfo != null) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("isBeautiful", userInfo.getIsBeautiful() + "");
                    map.put("userId", userInfo.getId());
                    map.put("userName", userInfo.getUserName());
                    map.put("userNumber", userInfo.getUserNumber() + "");
                    map.put("userPicture", userInfo.getUserPicture());
                    String user = new Gson().toJson(map);

                    webView.loadUrl("javascript:getUser('" + user + "')");
                }

                super.onPageFinished(view, url);

            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

//               if (!TextUtils.isEmpty(title)) {
//                   textView.setText(title);
//               }

            }
        });


    }

}
