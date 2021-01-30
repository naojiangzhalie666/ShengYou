package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import okhttp3.MultipartBody;

public interface FamilyCreateContract {

    interface View extends BaseView {

        /**
         * 照相机
         */
        void camera();

        /**
         * 图库
         */
        void gallery();

        /**
         * 请求错误
         *
         * @param msg
         */
        void onError(String msg);

        /**
         * 获取申请状态成功
         *
         * @param status
         */
        void ApplyStatusSuccess(String status);

        /**
         * 创建家族请求成功
         * @param msg
         */
        void createFamilySuccess(String msg);


    }

    interface Presenter {
        /**
         * 选择图片
         */
        void selectPhoto();

        /**
         * @param userId
         */
        void getFamilyApplyStatu(String userId);

        /**
         * 创建家族
         * @param clanName 家族名称
         * @param applicantid 申请者id
         * @param applyComment 备注
         * @param filePath 家族图片文件
         */
        void createFamily(String clanName,String applicantid,String applyComment,String filePath);

    }

    interface Model {

        /**
         * 查询用户是否有待审核家族申请
         *
         * @param userId 用户ID
         * @return
         */
        HttpObservable<BaseResponse<String>> getFamilyApplyStatu(String userId);

        /**
         * 创建家族
         * @param clanName 	家族名称
         * @param applicantId 	申请者id
         * @param applyComment	备注
         * @param file	家族图片文件
         * @return
         */
        HttpObservable<BaseResponse<String>> createFamily(String clanName, String applicantId,
                                                          String applyComment, MultipartBody.Part file);

    }
}
