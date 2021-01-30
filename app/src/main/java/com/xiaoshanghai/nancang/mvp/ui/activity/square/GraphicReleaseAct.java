package com.xiaoshanghai.nancang.mvp.ui.activity.square;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.mvp.contract.GraphiceContract;
import com.xiaoshanghai.nancang.mvp.presenter.GraphicePresenter;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

public class GraphicReleaseAct extends BaseMvpActivity<GraphicePresenter> implements GraphiceContract.View {


    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_num)
    TextView mTvNum;

    public static final int RC_CHOOSE_PHOTO = 1;
    public static final int RC_PHOTO_PREVIEW = 2;

    @Override
    public int setLayoutId() {
        return R.layout.activity_graphic_release;
    }

    @Override
    protected GraphicePresenter createPresenter() {
        return new GraphicePresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPresenter.attachView(this);
        mPresenter.initData(mPhotosSnpl);

    }

    @OnTextChanged(R.id.et_content)
    void changedText(Editable editable) {
        tvRelease.setEnabled(mEtContent.length() > 0 && mEtContent.length() <= 500);
        mTvNum.setText(mEtContent.length() + "/500");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }

    }

    @OnClick({R.id.iv_black, R.id.tv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                finish();
                break;
            case R.id.tv_release:
                mPresenter.release(mEtContent.getText().toString(), mPhotosSnpl.getData());
                break;
        }
    }


    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}
