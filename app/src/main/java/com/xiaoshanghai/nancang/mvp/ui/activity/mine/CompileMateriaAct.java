package com.xiaoshanghai.nancang.mvp.ui.activity.mine;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.xiaoshanghai.nancang.BuildConfig;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.CacheConstant;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.CompileMateriaContract;
import com.xiaoshanghai.nancang.mvp.presenter.CompileMateriaPresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.bean.AvatarResult;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.net.bean.UserPicturesResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.FileUtils;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.utils.UCropUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CompileMateriaAct extends BaseMvpActivity<CompileMateriaPresenter> implements CompileMateriaContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.civ_avarar)
    CircleImageView civAvarar;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.tv_self)
    TextView tvSelf;

    private Uri uri;
    private File cameraSavePath;
    private String photoPath;
    private MineReslut reslut;

    @Override
    public int setLayoutId() {
        return R.layout.activity_compile_materia;
    }

    @Override
    protected CompileMateriaPresenter createPresenter() {
        return new CompileMateriaPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);

        reslut = CacheConstant.result;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setMineResult(reslut);
    }

    private void setMineResult(MineReslut result) {

        GlideAppUtil.loadImage(this, result.getUserPicture(), civAvarar);

        tvNickName.setText(result.getUserName());
        String birthday = result.getUserBirthday();
        String[] s = birthday.split(" ");
        String userBirthday = "";
        if (s.length >= 1) {
            userBirthday = s[0];
        }
        tvBirthday.setText(userBirthday);

        String userIntroduce = result.getUserIntroduce();
        if (TextUtils.isEmpty(userIntroduce)) {
            tvSelf.setText("请设置");
        } else {
            tvSelf.setText(userIntroduce);
        }

        List<UserPicturesResult> userPictures = result.getUserPictures();
        if (userPictures != null && userPictures.size() > 0) {
            tvPhoto.setText(userPictures.size() + "张照片");
        } else {
            tvPhoto.setText("您暂时还没有上传照片哦...");
        }


    }

    @OnClick({R.id.ll_avatar, R.id.ll_nick_name, R.id.ll_birthday, R.id.ll_photo, R.id.ll_self})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
                mPresenter.selectPhoto();
                break;
            case R.id.ll_nick_name:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.MINE_RESLUT, reslut);
                ActStartUtils.startForAct(this, ModifyNickNameAct.class, bundle, Constant.NICK_NAME_REQUEST_CODE);
                break;
            case R.id.ll_birthday:
                mPresenter.selectTime(reslut.getUserBirthday());
                break;
            case R.id.ll_photo:
                Bundle photoBundle = new Bundle();
                photoBundle.putSerializable(Constant.MINE_RESLUT, reslut);
                ActStartUtils.startForAct(this, MyPhotosAct.class, photoBundle, Constant.MY_PHOTOS);
                break;
            case R.id.ll_self:
                Bundle selfbundle = new Bundle();
                selfbundle.putSerializable(Constant.MINE_RESLUT, reslut);
                ActStartUtils.startForAct(this, SelfIntroductionAct.class, selfbundle, Constant.SELF_REQUEST_CODE);
                break;
        }
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

            mPresenter.upAvatar(photoPath);

        }

        if (requestCode == Constant.NICK_NAME_REQUEST_CODE && resultCode == RESULT_OK) {
            String nickName = data.getStringExtra(Constant.NICK_NAME);
            reslut.setUserName(nickName);

            CacheConstant.result.setUserName(nickName);

            UserBean userInfo = SPUtils.getInstance().getUserInfo();
            userInfo.setUserName(nickName);

            String strUser = new Gson().toJson(userInfo);
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);

            tvNickName.setText(nickName);
        }

        if (requestCode == Constant.SELF_REQUEST_CODE && resultCode == RESULT_OK) {
            String self = data.getStringExtra(Constant.SELF);
            reslut.setUserIntroduce(self);

            CacheConstant.result.setUserIntroduce(self);
            UserBean userInfo = SPUtils.getInstance().getUserInfo();
            userInfo.setUserIntroduce(self);

            String strUser = new Gson().toJson(userInfo);
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);

            String userIntroduce = reslut.getUserIntroduce();
            if (TextUtils.isEmpty(userIntroduce)) {
                tvSelf.setText("请设置");
            } else {
                tvSelf.setText(userIntroduce);
            }

        }
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
    public void setTime(String date) {
        tvBirthday.setText(date);
        mPresenter.editBirthday(date);
    }

    @Override
    public void avatarSuccess(AvatarResult result) {
        if (result.getStatus() == 1) {
            GlideAppUtil.loadImage(this, result.getUrl(), civAvarar);
            CacheConstant.result.setUserPicture(result.getUrl());
            UserBean userInfo = SPUtils.getInstance().getUserInfo();
            userInfo.setUserPicture(result.getUrl());

            String strUser = new Gson().toJson(userInfo);
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
        } else if (result.getStatus() == 0) {
            ToastUtils.showShort("请求出错");
        }
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void birthdaySuccess(String birthday) {
        UserBean userInfo = SPUtils.getInstance().getUserInfo();
        userInfo.setUserBirthday(birthday);
        CacheConstant.result.setUserBirthday(birthday);
        reslut.setUserBirthday(birthday);

        String strUser = new Gson().toJson(userInfo);
        SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
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
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}
