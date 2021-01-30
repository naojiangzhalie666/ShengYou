package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.FamilyMemberContract;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult;

import java.util.List;

public class FamilyMemberPresenter extends BasePresenter<FamilyMemberContract.View> implements FamilyMemberContract.Presenter  {

    private FamilyMemberModel model;

    public FamilyMemberPresenter() {
        this.model = new FamilyMemberModel();
    }

    @Override
    public void getClanInfo(String clanId) {
        model.getClanInfo(clanId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<ClanResult>() {
                    @Override
                    protected void success(ClanResult bean, BaseResponse<ClanResult> response) {
                        getView().clanInfoSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void familyMembers(String clanId) {
        model.familyMembers(clanId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<FamilyMemberResult>>() {
                    @Override
                    protected void success(List<FamilyMemberResult> results, BaseResponse<List<FamilyMemberResult>> response) {
                        getView().familyMembersSuccess(results);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void joinFamily(String userId, String clanId) {
        model.joinFamily(userId, clanId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {

                        getView().joinSuccess(bean);

                    }

                    @Override
                    protected void error(String msg) {

                        getView().onError(msg);

                    }
                });
    }

    @Override
    public void outFamily(String userId, String clanId) {
        model.outFamily(userId, clanId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().outSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }



}
