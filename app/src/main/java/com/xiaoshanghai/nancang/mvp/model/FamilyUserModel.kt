package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.FamilyUserContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult
import com.xiaoshanghai.nancang.net.bean.UserIncome

class FamilyUserModel: FamilyUserContract.Model {
    override fun getUserIncome(current: String?, size: String?, userId: String?, clanId: String?,startTime: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<UserIncome>>>> {
        return HttpClient.getApi().getUserIncome(current, size, userId, clanId,startTime)
    }

    override fun getFamilyInfo(userId: String?): HttpObservable<BaseResponse<MyFamilyResult>> {
        return HttpClient.getApi().getFamilyInfo(userId)
    }

}