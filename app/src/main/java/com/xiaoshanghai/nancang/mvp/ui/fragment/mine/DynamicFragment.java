package com.xiaoshanghai.nancang.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.callback.FriendFabulousCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.DynamicContract;
import com.xiaoshanghai.nancang.mvp.presenter.DynamicPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ReportAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.MessageDetailsAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.DynamicAdapter;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.PhotoCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class DynamicFragment extends BaseMvpFragment<DynamicPresenter> implements DynamicContract.View, OnRefreshLoadMoreListener, PhotoCallback, FriendFabulousCallback {

    @BindView(R.id.rl_view)
    RecyclerView mRlView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private String userId;

    private DynamicAdapter mAdapter;

    public static DynamicFragment newInstance() {
        DynamicFragment fragment = new DynamicFragment();
        return fragment;
    }

    @Override
    protected DynamicPresenter createPresenter() {
        return new DynamicPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void onLazyLoad() {
        mAdapter = new DynamicAdapter(this);
        mAdapter.setFabulousCallback(this);
        mRlView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRlView.setAdapter(mAdapter);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        userId = arguments.getString(Constant.USER_ID);
        mPresenter.attachView(this);
        mRlView.setNestedScrollingEnabled(false);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        if (!TextUtils.isEmpty(userId)) {
            mPresenter.getFriendsCircle(null, userId);
        }

    }

    /**
     * ??????
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getFriendsCircle(refreshLayout, userId);
    }

    /**
     * ??????
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFriendsCircle(refreshLayout, userId);
    }

    /**
     * ?????????????????????
     *
     * @param refreshLayout
     * @param roomResults
     */
    @Override
    public void refresh(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults) {
        if (refreshLayout != null)
            refreshLayout.finishRefresh();
        mAdapter.setList(roomResults);
        if (roomResults.size() <= 0) {
            if (refreshLayout != null)
                refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (refreshLayout != null)
                refreshLayout.resetNoMoreData();
        }
    }

    /**
     * ?????????????????????
     *
     * @param refreshLayout
     * @param roomResults
     */
    @Override
    public void loadMore(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults) {
        mAdapter.addData(roomResults);

        if (roomResults.size() <= 0) {
            if (refreshLayout != null)
                refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (refreshLayout != null)
                refreshLayout.finishLoadMore();
        }
    }

    /**
     * ??????????????????
     *
     * @param refreshLayout
     * @param msg
     */
    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {

    }

    /**
     * ????????????
     *
     * @param msg
     */
    @Override
    public void fabulousSuccess(String msg) {

    }

    @Override
    public void onDeleteSuccess(String status, FriendsCircleResult result) {
        if (status.equals("1")){
            mAdapter.remove(result);
        }
    }

    /**
     * ??????????????????
     *
     * @param index
     * @param imageInfo
     */
    @Override
    public void onItemPhotoClick(int index, List<ImageInfo> imageInfo) {
        ArrayList<String> imgs = new ArrayList<>();

        for (ImageInfo info : imageInfo) {
            imgs.add(info.getBigImageUrl());
        }

        photoPreviewWrapper(index, imgs);
    }

    private void photoPreviewWrapper(int index, ArrayList<String> imageInfo) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(getActivity())
                .saveImgDir(new File(Constant.FILE_PATH)); // ????????????????????????????????? null??????????????????????????????

        if (imageInfo.size() == 1) {
            // ??????????????????
            photoPreviewIntentBuilder.previewPhoto(imageInfo.get(index));
        } else if (imageInfo.size() > 1) {
            // ??????????????????
            photoPreviewIntentBuilder.previewPhotos(imageInfo)
                    .currentPosition(index); // ???????????????????????????
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    /**
     * ????????????
     *
     * @param result
     * @param position
     */
    @Override
    public void onClickFabulous(FriendsCircleResult result, int position) {
        int hasLike = result.getHasLike();//????????????
        int likeNumber = result.getLikeNumber();//?????????
        if (hasLike == 1) {
            result.setHasLike(0);
            result.setLikeNumber(likeNumber - 1);
        } else if (hasLike == 0) {
            result.setHasLike(1);
            result.setLikeNumber(likeNumber + 1);
        }
        mAdapter.notifyDataSetChanged();
        mPresenter.fabulous(result.getId());
    }

    /**
     * ????????????
     *
     * @param result
     * @param position
     */
    @Override
    public void onClickComment(FriendsCircleResult result, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.FRIEND_CIRCLE_RESULT, result);
        ActStartUtils.startAct(getActivity(), MessageDetailsAct.class, bundle);
//        ToastUtils.showShort("??????");
    }

    /**
     * ????????????
     *
     * @param result
     * @param position
     */
    @Override
    public void onClickShare(FriendsCircleResult result, int position) {

        TipsDialog.createDialog(getActivity(), R.layout.dialog_share)
                .bindClick(R.id.tv_cancel, (v, dialog) -> {
                    dialog.dismiss();
                })
                .bindClick(R.id.tv_share_buddy, (v, dialog) -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BUDDY_KEY, Constant.TOPIC);
                    bundle.putSerializable(Constant.TOPIC, result);
                    ActStartUtils.startAct(getActivity(), MyBuddyAct.class, bundle);
                })
                .show();

    }

    /**
     * ??????????????????
     *
     * @param result
     * @param position
     */
    @Override
    public void onClickAvater(FriendsCircleResult result, int position) {

    }

    /**
     * ??????????????????
     *
     * @param result
     * @param position
     */
    @Override
    public void onClickMore(FriendsCircleResult result, int position) {
        more(result, SPUtils.getInstance().getUserInfo().getId());
    }

    @Override
    public void onClickChat(FriendsCircleResult result, int position) {

    }

    @Override
    public void onClickVideo(FriendsCircleResult result, int position) {

    }

    /**
     * ??????myUserId ???????????????????????????????????????
     *
     * @param result
     * @param myUserId
     */
    private void more(FriendsCircleResult result, String myUserId) {

        String userId = result.getUserId();

        TipsDialog tipsDialog = TipsDialog.createDialog(getActivity(), R.layout.dialog_select_camera)
                .setTextColor(R.id.tv_camera, R.color.color_black)
                .setTextColor(R.id.tv_photo, R.color.color_aaaaaa)
                .setText(R.id.tv_photo, "??????")
                .bindClick(R.id.tv_camera, (v, dialog) -> {
                    dialog.dismiss();
                    if (userId.equals(myUserId)) {      //??????
//                        ToastUtils.showShort("??????");
                        mPresenter.deleteTopic(result.getId(),result);

                    } else {         //??????
//                        ToastUtils.showShort("??????");
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.REPOTR_TYPE,Constant.REPORT_TYPE_USER);
                        bundle.putString(Constant.REPORT_ID,result.getUserId());
                        ActStartUtils.startAct(getActivity(), ReportAct.class,bundle);
                        dialog.dismiss();
                    }
                })
                .bindClick(R.id.tv_photo, (v, dialog) -> {
                    dialog.dismiss();

                });


        if (userId.equals(myUserId)) {

            tipsDialog.setText(R.id.tv_camera, "??????");
        } else {
            tipsDialog.setText(R.id.tv_camera, "??????");
        }

        tipsDialog.show();
    }
}
