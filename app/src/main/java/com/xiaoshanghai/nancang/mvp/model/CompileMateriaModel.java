package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.CompileMateriaContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.AvatarResult;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import okhttp3.MultipartBody;

public class CompileMateriaModel implements CompileMateriaContract.Model {
    @Override
    public HttpObservable<BaseResponse<AvatarResult>> upAvatar(MultipartBody.Part file) {
        return HttpClient.getApi().upAvatar(file);
    }

    @Override
    public HttpObservable<BaseResponse<String>> editBirthday(String birthday) {
        return HttpClient.getApi().editBirthday(birthday);
    }
}
