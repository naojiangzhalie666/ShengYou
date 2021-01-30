package com.xiaoshanghai.nancang.mvp.ui.activity.square;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.xiaoshanghai.nancang.BuildConfig;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.FamilyCreateContract;
import com.xiaoshanghai.nancang.mvp.presenter.FamilyCreatePresenter;
import com.xiaoshanghai.nancang.mvp.ui.view.EditTextView;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.FileUtils;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.utils.UCropUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.yalantis.ucrop.UCrop;


import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class FamilyCreateAct extends BaseMvpActivity<FamilyCreatePresenter> implements FamilyCreateContract.View {


    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.et_family_name)
    EditText etFamilyName;
    @BindView(R.id.iv_family_logo)
    ImageView ivFamilyLogo;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.et_family_remark)
    EditTextView etFamilyRemark;
    @BindView(R.id.tv_applicant)
    TextView mTvApplicant;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private Uri uri;
    private File cameraSavePath;
    private String photoPath;
    private String status;
    private UserBean userInfo;

    @Override
    public int setLayoutId() {
        return R.layout.activity_family_create;
    }

    @Override
    protected FamilyCreatePresenter createPresenter() {
        return new FamilyCreatePresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mPresenter.attachView(this);

        userInfo = SPUtils.getInstance().getUserInfo();
        mTvApplicant.setText(userInfo.getUserName());
        mPresenter.getFamilyApplyStatu(userInfo.getId());

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
            GlideAppUtil.loadImage(this, photoPath, ivFamilyLogo);
            ivDelete.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_black, R.id.tv_record, R.id.iv_family_logo, R.id.iv_delete, R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_record:
                ActStartUtils.startAct(FamilyCreateAct.this, FamilyApplicationRecordAct.class);
                break;
            case R.id.iv_family_logo:
                if (TextUtils.isEmpty(photoPath)) {
                    mPresenter.selectPhoto();
                } else {
                    photoPreviewWrapper(photoPath);
                }
                break;
            case R.id.iv_delete:
                removePhoto();
                break;
            case R.id.tv_submit:
                createFamily();
                break;
        }
    }

    private void createFamily() {
        String familyName = etFamilyName.getText().toString().trim();

        if (TextUtils.isEmpty(familyName)) {
            ToastUtils.showShort("请输入家族名");
            return;
        }

        if (TextUtils.isEmpty(photoPath)) {
            ToastUtils.showShort("请上传家族头像");
            return;
        }

        String familyRemark = etFamilyRemark.getText().toString().trim();

        if (TextUtils.isEmpty(familyRemark)) {
            ToastUtils.showShort("请输入申请理由");
            return;
        }

        mPresenter.createFamily(familyName,userInfo.getId(),familyRemark,photoPath);
    }

    private void removePhoto() {
        uri = null;
        cameraSavePath = null;
        photoPath = null;
        ivDelete.setVisibility(View.GONE);
        ivFamilyLogo.setImageResource(R.mipmap.square_release_increase);
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

    private void photoPreviewWrapper(String photoPath) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                .saveImgDir(null); // 保存图片的目录，如果传 null，则没有保存图片功能

        // 预览单张图片
        photoPreviewIntentBuilder.previewPhoto(photoPath);

        startActivity(photoPreviewIntentBuilder.build());
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
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void ApplyStatusSuccess(String status) {
        this.status = status;
        if (!TextUtils.isEmpty(status)) {
            if ("0".equals(status)) {
                mTvSubmit.setEnabled(true);
            } else if ("1".equals(status)) {
                mTvSubmit.setEnabled(false);
                auditStatus();
            }
        }
    }

    @Override
    public void createFamilySuccess(String msg) {
        if ("0".equals(msg)){
            ToastUtils.showShort("创建失败");
        } else if ("1".equals(msg)){
            mTvSubmit.setEnabled(false);
            auditStatus();
        }
    }

    private void auditStatus() {

        TipsDialog tipsDialog = TipsDialog.createDialog(this, R.layout.dialog_audit)
                .setCancelable(true)
                .setCanceledOnTouchOutside(false);
        if (status.equals("0")) {
            tipsDialog.setText(R.id.tv_title, getResources().getString(R.string.application_submitted));
            tipsDialog.setText(R.id.tv_two_title, getResources().getString(R.string.moderated));
        } else if (status.equals("1")) {
            tipsDialog.setText(R.id.tv_title, getResources().getString(R.string.family_under_review));
            tipsDialog.setText(R.id.tv_two_title, getResources().getString(R.string.family_please_wait));
        }
        tipsDialog.bindClick(R.id.tv_confirm, (v, dialog) -> {
            if (status.equals("0")) {
                finish();
            } else if (status.equals("1")) {
                ActStartUtils.startAct(FamilyCreateAct.this, FamilyApplicationRecordAct.class);
            }

        }).show();

    }

}
