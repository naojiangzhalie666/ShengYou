package com.xiaoshanghai.nancang.mvp.ui.fragment.square;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.callback.FriendFabulousCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.RecommendContract;
import com.xiaoshanghai.nancang.mvp.presenter.RecommendPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ReportAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.MessageDetailsAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.FriendsCircleAdapter;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
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

public class AttentionFragment extends BaseMvpFragment<RecommendPresenter> implements RecommendContract.View, OnRefreshLoadMoreListener, PhotoCallback, OnItemClickListener, FriendFabulousCallback {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_view)
    RecyclerView rlView;

    private FriendsCircleAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected RecommendPresenter createPresenter() {
        return new RecommendPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void onLazyLoad() {

        mAdapter = new FriendsCircleAdapter(getActivity(), this);
        mAdapter.setFabulousCallback(this);
        rlView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlView.setAdapter(mAdapter);
        refreshLayout.autoRefresh();

    }

    @Override
    public void refresh(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults) {
        refreshLayout.finishRefresh();
        mAdapter.setList(roomResults);


        if (roomResults.size() <= 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.resetNoMoreData();
        }

    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults) {
        mAdapter.addData(roomResults);

        if (roomResults.size() <= 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {

    }

    @Override
    public void fabulousSuccess(String msg) {

    }

    @Override
    public void onDeleteSuccess(String status, FriendsCircleResult result) {
        if (status.equals("1")) {
            mAdapter.remove(result);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getAttention(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAttention(refreshLayout);
    }


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
                .saveImgDir(new File(Constant.FILE_PATH)); // 保存图片的目录，如果传 null，则没有保存图片功能

        if (imageInfo.size() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(imageInfo.get(index));
        } else if (imageInfo.size() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos(imageInfo)
                    .currentPosition(index); // 当前预览图片的索引
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ToastUtils.showShort("position:" + position);
    }

    @Override
    public void onClickFabulous(FriendsCircleResult result, int position) {
        int hasLike = result.getHasLike();//是否点赞
        int likeNumber = result.getLikeNumber();//点赞数
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

    @Override
    public void onClickComment(FriendsCircleResult result, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.FRIEND_CIRCLE_RESULT, result);
        ActStartUtils.startAct(getActivity(), MessageDetailsAct.class, bundle);
//        ToastUtils.showShort("留言");
    }

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
//        ToastUtils.showShort("分享");
    }

    @Override
    public void onClickAvater(FriendsCircleResult result, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_ID, result.getUserId());
        ActStartUtils.startAct(getActivity(), HomePageAct.class, bundle);
    }

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
     * 根据myUserId 判断是否为自己发出的朋友圈
     *
     * @param result
     * @param myUserId
     */
    private void more(FriendsCircleResult result, String myUserId) {

        String userId = result.getUserId();

        TipsDialog tipsDialog = TipsDialog.createDialog(getActivity(), R.layout.dialog_select_camera)
                .setTextColor(R.id.tv_camera, R.color.color_black)
                .setTextColor(R.id.tv_photo, R.color.color_aaaaaa)
                .setText(R.id.tv_photo, "取消")
                .bindClick(R.id.tv_camera, (v, dialog) -> {
                    dialog.dismiss();
                    if (userId.equals(myUserId)) {      //删除
//                        ToastUtils.showShort("删除");
                        mPresenter.deleteTopic(result.getId(),result);

                    } else {         //举报
//                        ToastUtils.showShort("举报");
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

            tipsDialog.setText(R.id.tv_camera, "删除");
        } else {
            tipsDialog.setText(R.id.tv_camera, "举报");
        }

        tipsDialog.show();
    }
}
