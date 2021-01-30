package com.xiaoshanghai.nancang.net.bean;

import android.text.TextUtils;

import com.xiaoshanghai.nancang.utils.StringUtils;

public class BaseResponse<T> extends BaseResult {

    private final static int STATUS_SUCCEED = 0;

    private int code;

    private int status;

    private T data;

    private String msg;

    public BaseResponse() {
    }

    public boolean isSucceed() {
        return status == STATUS_SUCCEED;
    }

    public boolean tokenExpire() {
        return status == 401 || status == 301002 || status == 301003 || status == 301004 || status == 301005;
//        return code == 301004;//token无效
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getRealMsg() {
        if (!TextUtils.isEmpty(msg))
            return StringUtils.strNoNull(msg);
        else
            return "";

    }
}
