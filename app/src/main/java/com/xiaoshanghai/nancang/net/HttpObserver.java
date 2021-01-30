package com.xiaoshanghai.nancang.net;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.utils.AppActivityStatusUtil;
import com.xiaoshanghai.nancang.utils.LogUtils;
import com.xiaoshanghai.nancang.utils.NetworkUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class HttpObserver<T> implements Observer<BaseResponse<T>> {

    @Override
    final public void onSubscribe(Disposable d) {
        LogUtils.d("API 建立连接，开始请求");
        start();
    }

    @Override
    public void onNext(BaseResponse<T> t) {
        if (t.isSucceed()) {
//            LogUtils.d("API 请求成功.");
            success(t.getData(), t);
        } else {
            String msg = t.getRealMsg();
            if (t.tokenExpire()) {
                //如果token失效 置空msg 则不会弹出信息
                msg = "";
                AppActivityStatusUtil.backToLogin();
            }
            boolean b = execDataByCode(t.getStatus(), t.getData(), msg);
            if (b) return;
            error(msg);
            LogUtils.d("API 请求失败!");
        }
    }

    @Override
    final public void onError(Throwable e) {
        LogUtils.e("API 请求出错! " + e.getMessage());

        String message;
        if (!NetworkUtils.isConnected()) {


            message = "网络不可用";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            Response response = httpException.response();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                message = jObjError.optString("msg");
                boolean b = execDataByCode(jObjError.optInt("status"), null, jObjError.optString("msg"));
                if (b) return;
            } catch (Exception e1) {
                message = httpException.code() + " " + httpException.getMessage();
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            message = "解析错误";
        } else if (e instanceof RuntimeException) {
            message = "运行错误";
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            message = "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {
            message = "连接超时";
        } else if (e instanceof java.net.SocketTimeoutException) {
            message = "连接超时";
        } else {
            message = "未知错误";
        }

        error(message);
    }

    @Override
    final public void onComplete() {
        LogUtils.d("API 结束请求.");
    }

    /**
     * 开始请求
     */
    protected void start() {

    }

    /**
     * 请求成功同时业务成功的情况下会调用此函数
     */
    protected abstract void success(T bean, BaseResponse<T> response);

    /**
     * 自己处理特殊code true 自己处理
     */
    protected boolean execDataByCode(int code, T data, String msg) {
        return false;
    }

    /**
     * 请求出错的情况下会调用此函数
     */
    protected abstract void error(String msg);

}
