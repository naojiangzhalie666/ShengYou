package com.xiaoshanghai.nancang.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.RegisterContract;
import com.xiaoshanghai.nancang.mvp.model.RegisterModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.RegisterActivity;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.xiaoshanghai.nancang.utils.DateUtil;
import com.xiaoshanghai.nancang.utils.FileUtils;
import com.xiaoshanghai.nancang.utils.PermissionUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RegisterModel model;

    public RegisterPresenter() {
        model = new RegisterModel();
    }

    @Override
    public void selectPhoto() {
        RxPermissions rxPermissions = new RxPermissions((RegisterActivity) getView());
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
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
                getView().setTime(dateToString);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("请选择生日")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(calendar)// 如果不设置的话，默认是系统时间*/
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setDecorView(((Activity) (getView())).getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvTime.show();

    }

    @Override
    public void register(String filePath, String userName, String userPhone, String userBirthday, String userSex, String wechatOpenid) {

        getView().showLoading();

        String fileStr = FileUtils.qualityCompress(filePath);

        File file = new File(fileStr);
        File file2 = new File(file.getAbsolutePath());

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file2.getName(), photoRequestBody);
        model.register(body, userName, userPhone, userBirthday, userSex, wechatOpenid)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {
                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();

                        if (bean == null) {
                            getView().registerError(((Context) getView()).getResources().getString(R.string.register_error));
                            return;
                        }
                        switch (bean.getStatus()) {
                            case Constant.REGISTER_SUCCESS:

//                                String strUser = new Gson().toJson(bean.getUser());
//                                SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
//                                SPUtils.getInstance().put(SpConstant.APP_TOKEN, bean.getToken());
                                getView().registerSuccess(bean);

                                break;
                            case Constant.REGISTER_ERROR:
                                getView().registerError(((Context) getView()).getResources().getString(R.string.register_error));
                                break;
                            case Constant.REGISTER_REPEAT:
                            case "10":
                                getView().registerError(((Context) getView()).getResources().getString(R.string.register_repeat));
                                break;
                            default:
                                ToastUtils.showLong(((Context) getView()).getResources().getString(R.string.register_error));
                                break;
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().registerError(msg);
                    }
                });
    }

    @Override
    public void getUserSig(LogonResult data) {

        getView().showLoading();

        model.getUserSig(data.getUser().getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().sigSuccess(data, bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().registerError(msg);
                    }
                });
    }

    @Override
    public void loginIm(LogonResult data, String sig, IUIKitCallBack callBack) {

        SPUtils.getInstance().put(SpConstant.APP_SIG, sig);

//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserId(data.getUser().getId());
//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserSig(sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((RegisterActivity) getView());
        trtcVoiceRoom.login(Constant.IM_APP_KEY, data.getUser().getId(), sig, (code, msg) -> {
            if (code == -1) {
                if (callBack!=null) {
                    callBack.onError("登录失败",code,msg);
                }
            } else {
//                if (TUIKitConfigs.getConfigs().getGeneralConfig().isSupportAVCall()) {
//                    UserModel self = new UserModel();
//                    self.userId = data.getUser().getId();
//                    self.userSig = sig;
//                    ProfileManager.getInstance().setUserModel(self);
//                    AVCallManager.getInstance().init(((RegisterActivity) getView()).getApplicationContext());
//                }
                callBack.onSuccess(null);
            }
        });

//        TUIKit.login(data.getUser().getId(), sig, callBack);
    }

}
