package com.xiaoshanghai.nancang.mvp.presenter;

import android.text.TextUtils;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.PublicChatContract;
import com.xiaoshanghai.nancang.mvp.model.PublicChatModel;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GiftAllResult;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

public class PublicChatPresenter extends BasePresenter<PublicChatContract.View> implements PublicChatContract.Presenter {

    private PublicChatModel model;

    public PublicChatPresenter() {
        model = new PublicChatModel();
    }

    @Override
    public void getUserInfo(String userId) {
        model.getUserInfo(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<VoiceRoomSeatEntity.UserInfo>() {
                    @Override
                    protected void success(VoiceRoomSeatEntity.UserInfo bean, BaseResponse<VoiceRoomSeatEntity.UserInfo> response) {
                        getView().onUserInfoSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void getFollowStatus(VoiceRoomSeatEntity.UserInfo userInfo) {
        model.queryFollow(userInfo.id)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onUserFollow(userInfo,bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void getGift() {
        model.getGift()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<GiftAllResult>(){

                    @Override
                    protected void success(GiftAllResult bean, BaseResponse<GiftAllResult> response) {
                        getView().onGiftSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void getSelfNoble() {
        model.getNoble()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onNobleSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void follow(String userId, String status) {
        model.follow(userId,status)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<String>(){

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (TextUtils.isEmpty(bean)) {
                            getView().onError("请求失败");
                        } else {
                            if (status == "1") {
                                getView().onFollow(bean);
                            } else if (status == "2") {
                                getView().onUnFollow(bean);
                            }
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
