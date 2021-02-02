package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.RecommendContract;
import com.xiaoshanghai.nancang.mvp.model.RecommendModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.LoginActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.GraphicReleaseAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;

import java.util.List;

public class RecommendPresenter extends BasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private RecommendModel model;

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public RecommendPresenter() {
        model = new RecommendModel();
    }

    @Override
    public void getFriendsCircle(RefreshLayout refreshLayout,String city, Context context) {
        if(TextUtils.isEmpty(city)) {
            model.getFriendsCircle(mPage + "", size + "", "")
                    .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<FriendsCircleResult>>>() {
                        @Override
                        protected void success(HomeRoomResult<List<FriendsCircleResult>> bean, BaseResponse<HomeRoomResult<List<FriendsCircleResult>>> response) {
                            List<FriendsCircleResult> records = bean.getRecords();
                            if (records != null) {
                                if (mPage == initPage) {
                                    getView().refresh(refreshLayout, records);
                                } else {
                                    getView().loadMore(refreshLayout, records);
                                }
                                if (records.size() > 0)
                                    mPage++;
                            }
                            //records字段为零时强弹出
                            if(bean.getRecords().size()==0){
                                TipsDialog.createDialog(context, R.layout.login_useradd)
                                        .setCancelable(true)
                                        .setCanceledOnTouchOutside(true)
                                        .bindClick(R.id.tv_cancel, (v, dialog) -> {
                                            ActStartUtils.startAct(context, GraphicReleaseAct.class);
                                        }).show();
                            }
                        }

                        @Override
                        protected void error(String msg) {
                            getView().onError(refreshLayout, msg);
                        }
                    });
        }else {
            model.getFriendsCircle(mPage + "", size + "", "",city)
                    .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<FriendsCircleResult>>>() {
                        @Override
                        protected void success(HomeRoomResult<List<FriendsCircleResult>> bean, BaseResponse<HomeRoomResult<List<FriendsCircleResult>>> response) {
                            List<FriendsCircleResult> records = bean.getRecords();
                            if (records != null) {
                                if (mPage == initPage) {
                                    getView().refresh(refreshLayout, records);
                                } else {
                                    getView().loadMore(refreshLayout, records);
                                }
                                if (records.size() > 0)
                                    mPage++;
                            }
                        }

                        @Override
                        protected void error(String msg) {
                            getView().onError(refreshLayout, msg);
                        }
                    });
        }
    }

    @Override
    public void getAttention(RefreshLayout refreshLayout) {
        model.getAttention(mPage + "", size + "")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<FriendsCircleResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<FriendsCircleResult>> bean, BaseResponse<HomeRoomResult<List<FriendsCircleResult>>> response) {
                        List<FriendsCircleResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refresh(refreshLayout, records);
                            } else {
                                getView().loadMore(refreshLayout, records);
                            }
                            if (records.size() > 0)
                                mPage++;
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout, msg);
                    }
                });
    }

    @Override
    public void fabulous(String topicId) {
        model.fabulous(topicId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            getView().fabulousSuccess(bean);
                        } else {
                            getView().onError(null, response.getMessage());
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null, msg);
                    }
                });
    }

    @Override
    public void deleteTopic(String topicId, FriendsCircleResult result) {
        model.deleteTopic(topicId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().onDeleteSuccess(bean, result);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null, msg);
                    }
                });
    }

}
