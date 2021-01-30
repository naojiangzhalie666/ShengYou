package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MyPhotosConstract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;

import java.util.List;

import okhttp3.MultipartBody;

public class MyPhotosModel implements MyPhotosConstract.Model {

    @Override
    public HttpObservable<BaseResponse<String>> addPicture(MultipartBody.Part file) {
        return HttpClient.getApi().addPicture(file);
    }

    @Override
    public HttpObservable<BaseResponse<String>> deletePhoto(String id) {
        return HttpClient.getApi().deletePhoto(id);
    }

    @Override
    public HttpObservable<BaseResponse<List<UserPicturesResult>>> getMyPhotos(String userId) {
        return HttpClient.getApi().getMyPhotos(userId);
    }

}
