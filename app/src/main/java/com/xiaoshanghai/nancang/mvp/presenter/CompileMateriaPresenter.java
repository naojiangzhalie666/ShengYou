package com.xiaoshanghai.nancang.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.CompileMateriaContract;
import com.xiaoshanghai.nancang.mvp.model.CompileMateriaModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.CompileMateriaAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.AvatarResult;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.utils.DateUtil;
import com.xiaoshanghai.nancang.utils.FileUtils;
import com.xiaoshanghai.nancang.utils.PermissionUtil;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CompileMateriaPresenter extends BasePresenter<CompileMateriaContract.View> implements CompileMateriaContract.Presenter {

    private CompileMateriaModel model;

    public CompileMateriaPresenter() {
        model = new CompileMateriaModel();
    }

    @Override
    public void selectPhoto() {
        RxPermissions rxPermissions = new RxPermissions((CompileMateriaAct) getView());
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                TipsDialog.createDialog((Context) getView(), R.layout.dialog_select_camera)
                        .bindClick(R.id.tv_camera, (v, dialog) -> {
                            getView().camera();
                            dialog.dismiss();
                        })
                        .bindClick(R.id.tv_photo, (v, dialog) -> {
                            getView().gallery();
                        })
                        .show();
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
    public void selectTime(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.stringToDate(date, DateUtil.DatePattern.ONLY_DAY));

        TimePickerView pvTime = new TimePickerBuilder((Context) getView(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
                getView().setTime(dateToString);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
                .setCancelText("??????")//??????????????????
                .setSubmitText("??????")//??????????????????
//                .setContentSize(18)//??????????????????
                .setTitleSize(20)//??????????????????
                .setTitleText("???????????????")//????????????
                .setOutSideCancelable(false)//???????????????????????????????????????????????????????????????
                .isCyclic(true)//??????????????????
                .setTitleColor(Color.BLACK)//??????????????????
                .setSubmitColor(Color.BLUE)//????????????????????????
                .setCancelColor(Color.BLUE)//????????????????????????
                .setTitleBgColor(Color.WHITE)//?????????????????? Night mode
                .setBgColor(Color.WHITE)//?????????????????? Night mode
                .setDate(calendar)// ?????????????????????????????????????????????*/
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .isDialog(false)//??????????????????????????????
                .setDecorView(((Activity) (getView())).getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvTime.show();
    }

    @Override
    public void upAvatar(String avatarPath) {

        if (TextUtils.isEmpty(avatarPath)) return;

        getView().showLoading();

//        File file = new File(avatarPath);
//        File file2 = new File(file.getAbsolutePath());

        String filePath = FileUtils.qualityCompress(avatarPath);
        File file = new File(filePath);
        File file2 = new File(file.getAbsolutePath());

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file2.getName(), photoRequestBody);

        model.upAvatar(body)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<AvatarResult>() {
                    @Override
                    protected void success(AvatarResult bean, BaseResponse<AvatarResult> response) {
                        getView().hideLoading();
                        getView().avatarSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });

    }

    @Override
    public void editBirthday(String birthday) {
        if (TextUtils.isEmpty(birthday)) return;
        String replace = birthday.replace("-", "/");
        getView().showLoading();
        model.editBirthday(replace)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        if ("1".equals(bean)) {
                            getView().birthdaySuccess(birthday);
                        } else {
                            getView().onError("??????????????????");

                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();

                        getView().onError(msg);
                    }
                });
    }
}
