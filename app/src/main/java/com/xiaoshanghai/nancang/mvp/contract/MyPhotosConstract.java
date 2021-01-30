package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;

import java.util.List;

import okhttp3.MultipartBody;

public interface MyPhotosConstract {

    interface View extends BaseView{

        /**
         * 请求错误
         * @param msg
         */
        void onError(String msg);

        /**
         * 照相机
         */
        void camera();

        /**
         * 图库
         */
        void gallery();

        /**
         * 获取相册请求成功
         * @param myPhotos
         */
        void myPhotoSuccess(List<UserPicturesResult> myPhotos);

        /**
         * 增加图片到相册请求成功
         * @param status
         */
        void upPhotoSuccess(String status);

        /**
         * 删除个人相册图片请求成功
         * @param status
         * @param position
         */
        void deleteSuccess(String status,int position);

    }

    interface Presenter {

        /**
         * 选择相册图片
         */
        void selectPhoto();

        /**
         * 上传图片到个人相册
         * @param photoPath
         */
        void upPhoto(String photoPath);

        /**
         * 删除个人相册中图片
         * @param id
         */
        void delete(String id,int position);

        /**
         * 查询个人相册
         * @param userId
         */
        void getMyPhotos(String userId);

    }

    interface Model {
        /**
         * 相册增加图片
         * @param file
         * @return
         */
        HttpObservable<BaseResponse<String>> addPicture( MultipartBody.Part file);

        /**
         * 删除个人相册中的图片
         * @param id
         * @return
         */
        HttpObservable<BaseResponse<String>> deletePhoto(String id);

        /**
         * 查询个人相册
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<List<UserPicturesResult>>> getMyPhotos(String userId);
    }

}
