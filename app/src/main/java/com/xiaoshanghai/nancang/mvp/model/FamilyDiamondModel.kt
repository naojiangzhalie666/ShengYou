package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.FamilyDiamondContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult

class FamilyDiamondModel: FamilyDiamondContract.Model {
    override fun getFamilyUser(current: String?, size: String?, userId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<FamilyMemberResult>>>> {
        return HttpClient.getApi().getFamilyUser(current, size, userId)
    }

    override fun getFamilyDiamondInfo(userId: String?): HttpObservable<BaseResponse<StartRecommendResult>> {
        return HttpClient.getApi().getFamilyDiamondInfo(userId)
    }
}