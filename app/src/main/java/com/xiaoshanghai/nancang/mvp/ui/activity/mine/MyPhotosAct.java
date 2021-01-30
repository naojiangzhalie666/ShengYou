package com.xiaoshanghai.nancang.mvp.ui.activity.mine;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xiaoshanghai.nancang.BuildConfig;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.OnItemDeleteCallback;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.CacheConstant;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.MyPhotosConstract;
import com.xiaoshanghai.nancang.mvp.presenter.MyPhotosPresenter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyPhotosAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;
import com.xiaoshanghai.nancang.utils.FileUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.utils.UCropUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class MyPhotosAct extends BaseMvpActivity<MyPhotosPresenter> implements MyPhotosConstract.View, TitleBarClickCallback {


    private static final String TAG = "MyPhotosAct";
    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.rcy_photo)
    RecyclerView rcyPhoto;


    private Uri uri;
    private File cameraSavePath;
    private String photoPath;
    private MineReslut mResult;
    private MyPhotosAdapter mAdapter;

    private ArrayList<UserPicturesResult> userPicturesList = new ArrayList<>();

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_photos;
    }

    @Override
    protected MyPhotosPresenter createPresenter() {
        return new MyPhotosPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
        initData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcyPhoto.setLayoutManager(gridLayoutManager);

        mAdapter = new MyPhotosAdapter();
        rcyPhoto.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserPicturesResult item = (UserPicturesResult) adapter.getItem(position);
                if (item.getId().equals("0")) {
                    if (userPicturesList.size() < 9) {
                        mPresenter.selectPhoto();
                    } else {
                        ToastUtils.showShort("照片已达最大上传数");
                    }
                } else {

                    ArrayList<String> photo = new ArrayList<>();

                    for (UserPicturesResult userPicturesResult : userPicturesList) {
                        String id = userPicturesResult.getId();
                        if (!id.equals("0")) {
                            photo.add(userPicturesResult.getUserPicture());
                        }
                    }

                    photoPreviewWrapper((position - 1), photo);
                }
            }
        });

        mAdapter.setOnItemDeleteCallback(new OnItemDeleteCallback() {
            @Override
            public void onItemDelete(int position) {
                UserPicturesResult item = mAdapter.getItem(position);

                mPresenter.delete(item.getId(), position);

            }
        });

        mPresenter.getMyPhotos(mResult.getId());

    }

    private void photoPreviewWrapper(int index, ArrayList<String> imageInfo) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                .saveImgDir(null); // 保存图片的目录，如果传 null，则没有保存图片功能

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

    private void initData() {

        Bundle extras = getIntent().getExtras();

        mResult = (MineReslut) extras.getSerializable(Constant.MINE_RESLUT);

        List<UserPicturesResult> userPictures = mResult.getUserPictures();

        photoAdd(userPictures);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.CAMERA) {

            String photoPath;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }

            UCropUtils.startUCrop(this, photoPath, Constant.CROP_RESULT, 1, 1);
        }

        if (resultCode == RESULT_OK && requestCode == Constant.GALLERY_RESULT) {
            Uri uri = data.getData();
            String filePathByUri = FileUtils.getFilePathByUri(this, uri);
            UCropUtils.startUCrop(this, filePathByUri, Constant.CROP_RESULT, 1, 1);
        }

        if (resultCode == RESULT_OK && requestCode == Constant.CROP_RESULT) {
            Uri resultUri = UCrop.getOutput(data);
            photoPath = FileUtils.getFilePathByUri(this, resultUri);

            mPresenter.upPhoto(photoPath);

        }


    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        mAdapter.setEdit();
    }


    private void photoAdd(List<UserPicturesResult> userPictures) {
        userPicturesList.clear();
        UserPicturesResult userPicturesResult = initList();
        userPicturesList.add(userPicturesResult);
        userPicturesList.addAll(userPictures);
    }

    private UserPicturesResult initList() {
        UserPicturesResult bean = new UserPicturesResult();
        bean.setId("0");
        return bean;

    }

    private void takePhoto() {

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".JPG");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, Constant.CAMERA);
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, Constant.GALLERY_RESULT);
    }


    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void camera() {
        takePhoto();
    }

    @Override
    public void gallery() {
        selectPhoto();
    }

    @Override
    public void myPhotoSuccess(List<UserPicturesResult> myPhotos) {

        photoAdd(myPhotos);

        mAdapter.setList(userPicturesList);

        if (CacheConstant.result != null) {
            CacheConstant.result.setUserPictures(myPhotos);
        }

    }

    @Override
    public void upPhotoSuccess(String status) {

        if (status.equals("1")) {
            mPresenter.getMyPhotos(mResult.getId());
        } else {
            ToastUtils.showShort("上传失败");
        }
    }

    @Override
    public void deleteSuccess(String status, int position) {
        if ("1".equals(status)) {
            mPresenter.getMyPhotos(mResult.getId());
        } else {
            ToastUtils.showShort("删除失败");
        }
    }
}
