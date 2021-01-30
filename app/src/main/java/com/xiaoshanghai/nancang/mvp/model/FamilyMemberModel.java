package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FamilyMemberContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult;

import java.util.List;

public class FamilyMemberModel implements FamilyMemberContract.Model {
    @Override
    public HttpObservable<BaseResponse<ClanResult>> getClanInfo(String clanId) {
        return HttpClient.getApi().getClanInfo(clanId);
    }

    @Override
    public HttpObservable<BaseResponse<List<FamilyMemberResult>>> familyMembers(String clanId) {
        return HttpClient.getApi().familyMembers(clanId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> joinFamily(String userId, String clanId) {
        return HttpClient.getApi().joinFamily(userId,clanId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> outFamily(String userId, String clanId) {
        return HttpClient.getApi().outFamily(userId, clanId);
    }
}
