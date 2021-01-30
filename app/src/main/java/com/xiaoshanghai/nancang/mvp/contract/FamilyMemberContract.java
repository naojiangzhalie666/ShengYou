package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult;

import java.util.List;


public interface FamilyMemberContract {

    interface View extends BaseView{

        /**
         * 获取家族信息成功
         * @param result
         */
        void clanInfoSuccess(ClanResult result);

        /**
         * 获取家族成员成功
         */
        void familyMembersSuccess(List<FamilyMemberResult> results);

        /**
         * 请求加入家族接口成功
         * @param data
         */
        void joinSuccess(String data);

        /**
         * 请求退出家族接口成功
         * @param data
         */
        void outSuccess(String data);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);


    }

    interface Presenter {

        /**
         * 根据家族id获取家族信息
         * @param clanId
         */
        void getClanInfo(String clanId);

        /**
         * 根据家族ID 获取家族成员
         * @param clanId
         * @return
         */
        void familyMembers(String clanId);

        /**
         * 退出家族
         */
        void outFamily(String userId,String clanId);

        /**
         * 加入家族
         * @param userId
         * @param clanId
         */
        void joinFamily(String userId,String clanId);

    }

    interface Model {

        /**
         * 根据id获取家族信息
         * @param clanId
         * @return
         */
        HttpObservable<BaseResponse<ClanResult>> getClanInfo(String clanId);

        /**
         * 根据家族ID 获取家族成员
         * @param clanId
         * @return
         */
        HttpObservable<BaseResponse<List<FamilyMemberResult>>> familyMembers(String clanId);

        /**
         * 加入家族
         * @param userId
         * @param clanId
         * @return
         */
        HttpObservable<BaseResponse<String>> joinFamily(String userId,String clanId);

        /**
         * 退出家族
         * @param userId
         * @param clanId
         * @return
         */
        HttpObservable<BaseResponse<String>> outFamily(String userId, String clanId);

    }
}
