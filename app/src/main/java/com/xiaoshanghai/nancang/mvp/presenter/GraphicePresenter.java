package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.GraphiceContract;
import com.xiaoshanghai.nancang.mvp.model.GraphiceModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.GraphicReleaseAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.utils.FileUtils;
import com.xiaoshanghai.nancang.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class GraphicePresenter extends BasePresenter<GraphiceContract.View> implements GraphiceContract.Presenter, BGASortableNinePhotoLayout.Delegate {

    private BGASortableNinePhotoLayout mPhotosSnpl;
    private GraphiceModel model;

    public GraphicePresenter() {
        model = new GraphiceModel();
    }

    @Override
    public void initData(BGASortableNinePhotoLayout mPhotosSnpl) {
        this.mPhotosSnpl = mPhotosSnpl;
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
    }

    @Override
    public void release(String content, List<String> files) {

        getView().showLoading();

        Map<String, RequestBody> paramsMap = new HashMap<>();
        if (files != null) {
            for (String datum : files) {
                String fileStr = FileUtils.qualityCompress(datum);
                File file = new File(fileStr);
                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                paramsMap.put("files\"; filename=\"" + file.getName(), fileBody);
            }
        } else {
            paramsMap = null;
        }


        model.releaseFriends(content, paramsMap)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        if (bean.equals("1")) {
                            getView().onSuccess();
                        } else {
                            getView().onError(response.getMessage());
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });


    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        RxPermissions rxPermissions = new RxPermissions((GraphicReleaseAct) getView());
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                choicePhotoWrapper();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {

            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

            }
        }, rxPermissions);


    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder((Context) getView())
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        ((GraphicReleaseAct) getView()).startActivityForResult(photoPickerPreviewIntent, GraphicReleaseAct.RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

    private void choicePhotoWrapper() {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Constant.FILE_PATH);

        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder((Context) getView())
                .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        ((GraphicReleaseAct) getView()).startActivityForResult(photoPickerIntent, GraphicReleaseAct.RC_CHOOSE_PHOTO);

    }
}
