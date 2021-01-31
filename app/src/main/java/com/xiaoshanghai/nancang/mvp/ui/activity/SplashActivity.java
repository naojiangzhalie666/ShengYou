package com.xiaoshanghai.nancang.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseApplication;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.SplashContract;
import com.xiaoshanghai.nancang.mvp.presenter.SplashPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.LoginActivity;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.MainActivity;
import com.xiaoshanghai.nancang.utils.AMapLocationTools;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.AppUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.View {


    private String token;

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        token = SPUtils.getInstance().getString(SpConstant.APP_TOKEN);
        checkPermission();
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }


    @Override
    public void startMainActivity() {
        AMapLocationTools.INSTANCE.startLocation(this, amapLocation -> {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    BaseApplication.city=amapLocation.getCity();
                   BaseApplication.latitude=amapLocation.getLatitude()+"";
                    BaseApplication.longitude=amapLocation.getLongitude()+"";
                    AMapLocationTools.INSTANCE.stopLocation();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("aa", "------location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        });
        new Handler().postDelayed(() -> {
            String version = SPUtils.getInstance().getString(Constant.VERSION);
            String appVersionName = AppUtils.getAppVersionName(SplashActivity.this);
            if (TextUtils.isEmpty(version) || !version.equals(appVersionName)) {
                ActStartUtils.startAct(SplashActivity.this, GuideAct.class);
            } else {

                if (TextUtils.isEmpty(token) || SPUtils.getInstance().getUserInfo() == null) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                }
            }
            finish();
        }, 1500);

    }

    public void checkPermission()
    {
        int targetSdkVersion = 0;
        String[] PermissionString={Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        try {
            final PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;//获取应用的Target版本
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Build.VERSION.SDK_INT是获取当前手机版本 Build.VERSION_CODES.M为6.0系统
            //如果系统>=6.0
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                //第 1 步: 检查是否有相应的权限
                boolean isAllGranted = checkPermissionAllGranted(PermissionString);
                if (isAllGranted) {
                    //Log.e("err","所有权限已经授权！");
                    mPresenter.startMainActivity();
                    return;
                }
                // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
                ActivityCompat.requestPermissions(this,
                        PermissionString, 1);
            }
        }
    }
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false;
            }
        }
        return true;
    }

    //申请权限结果返回处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 所有的权限都授予了
                mPresenter.startMainActivity();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //容易判断错
                checkPermission();

            }
        }
    }
}
