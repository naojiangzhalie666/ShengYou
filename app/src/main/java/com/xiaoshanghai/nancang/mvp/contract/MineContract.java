package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.CustomerServiceEntity;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;


public interface MineContract {

    interface View extends BaseView{

        /**
         * 获取个人基本资料成功
         * @param result
         */
        void MineSuccess(MineReslut result);

        /**
         * 点击家族跳转
         * @param status 状态 0 没有家族，1 有家族 族长， 2 有家族不是族长
         * @param clanId 家族id
         * @param userId 用户id
         */
        void familyOnSuccess(String status,String clanId,String userId);

        /**
         * 获取客服详情
         * @param entity
         */
        void onServiceSuccess(CustomerServiceEntity entity);

        /**
         * 获取个人基本资料失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter{
        /**
         * 获取个人基本资料
         * @param userId
         */
        void getMine(RefreshLayout refreshLayout,String userId);

        /**
         * 点击家族按钮业务逻辑
         * @param isClanElder
         * @param userId
         */
        void family(int isClanElder,String userId);

        /**
         * 获取客服详情
         */
        void getService();
    }

    interface Model {
        /**
         * 获取个人基本资料
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MineReslut>> getMine(String userId);

        /**
         * 根据userId查询家族
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId);

        /**
         * 获取客服详情
         * @return
         */
        HttpObservable<BaseResponse<CustomerServiceEntity>> getService();
    }

}
