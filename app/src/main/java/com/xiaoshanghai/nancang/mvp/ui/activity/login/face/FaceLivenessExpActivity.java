package com.xiaoshanghai.nancang.mvp.ui.activity.login.face;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.baidu.idl.face.platform.ui.utils.IntentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.xiaoshanghai.nancang.base.BaseApplication;
import com.xiaoshanghai.nancang.bean.BaiduFaceDetectBean;
import com.xiaoshanghai.nancang.bean.getBaiDUTokenBean;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.utils.HttpUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
public class FaceLivenessExpActivity extends FaceLivenessActivity implements
        TimeoutDialog.OnTimeoutDialogClickListener {
    Intent intent=new Intent();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加至销毁列表
        BaseApplication.addDestroyActivity(FaceLivenessExpActivity.this,
                "FaceLivenessExpActivity");
    }

    @Override
    public void onLivenessCompletion(FaceStatusNewEnum status, String message,
                                     HashMap<String, ImageInfo> base64ImageCropMap,
                                     HashMap<String, ImageInfo> base64ImageSrcMap, int currentLivenessCount) {
        super.onLivenessCompletion(status, message, base64ImageCropMap, base64ImageSrcMap, currentLivenessCount);
        if (status == FaceStatusNewEnum.OK && mIsCompletion) {
            // 获取最优图片
            getBestImage(base64ImageCropMap, base64ImageSrcMap);
        } else if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
            if (mViewBg != null) {
                mViewBg.setVisibility(View.VISIBLE);
            }
            showMessageDialog();
        }
    }
    String base64;
    /**
     * 获取最优图片
     * @param imageCropMap 抠图集合
     * @param imageSrcMap  原图集合
     */
    private void getBestImage(HashMap<String, ImageInfo> imageCropMap, HashMap<String, ImageInfo> imageSrcMap) {
        String bmpStr = null;
        // 将抠图集合中的图片按照质量降序排序，最终选取质量最优的一张抠图图片
        if (imageCropMap != null && imageCropMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list1 = new ArrayList<>(imageCropMap.entrySet());
            Collections.sort(list1, (o1, o2) -> {
                String[] key1 = o1.getKey().split("_");
                String score1 = key1[2];
                String[] key2 = o2.getKey().split("_");
                String score2 = key2[2];
                // 降序排序
                return Float.valueOf(score2).compareTo(Float.valueOf(score1));
            });

            // 获取抠图中的加密或非加密的base64
            int secType = mFaceConfig.getSecType();

            if (secType == 0) {
                base64 = list1.get(0).getValue().getBase64();
            } else {
                base64 = list1.get(0).getValue().getSecBase64();
            }
        }
        // 将原图集合中的图片按照质量降序排序，最终选取质量最优的一张原图图片
        if (imageSrcMap != null && imageSrcMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list2 = new ArrayList<>(imageSrcMap.entrySet());
            Collections.sort(list2, (o1, o2) -> {
                String[] key1 = o1.getKey().split("_");
                String score1 = key1[2];
                String[] key2 = o2.getKey().split("_");
                String score2 = key2[2];
                // 降序排序
                return Float.valueOf(score2).compareTo(Float.valueOf(score1));
            });
            bmpStr = list2.get(0).getValue().getBase64();
        }
        // 页面跳转
        IntentUtils.getInstance().setBitmap(bmpStr);
        postToken();
    }
      private    TimeoutDialog mTimeoutDialog;
    private void showMessageDialog() {
        mTimeoutDialog = new TimeoutDialog(this);
        mTimeoutDialog.setDialogListener(this);
        mTimeoutDialog.setCanceledOnTouchOutside(false);
        mTimeoutDialog.setCancelable(false);
        mTimeoutDialog.show();
        onPause();
    }
    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onRecollect() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog.dismiss();
        }
        if (mViewBg != null) {
            mViewBg.setVisibility(View.GONE);
        }
        onResume();
    }
    @Override
    public void onReturn() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog.dismiss();
        }
        finish();
    }
    public void  postToken(){
        HttpClient.getApi().getBaiDUToken("client_credentials",
                "fUCVLlhx0koKlUbA9PzgOty1",
                "y2xAQ4zFcQ5antIK1hmZGuEGdmKaBajg")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<getBaiDUTokenBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull getBaiDUTokenBean getBaiDUTokenBeanBaseResponse) {
                        postImge(getBaiDUTokenBeanBaseResponse.getAccess_token());
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showLong("获取Token失败！");
                        setResult(100);
                        finish();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 上传图片识别性别
     */
    public void postImge(String access_token){
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        final Observable<BaiduFaceDetectBean> observable = Observable.create((ObservableOnSubscribe<BaiduFaceDetectBean>) e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("image", base64);
            map.put("image_type", "BASE64");
            map.put("face_field", "gender");
            String param = GsonUtils.toJson(map);
            String accessToken = access_token;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            BaiduFaceDetectBean entity = new Gson().fromJson(result, BaiduFaceDetectBean.class);
            e.onNext(entity);
            e.onComplete();
        });
        DisposableObserver<BaiduFaceDetectBean> disposableObserver = new DisposableObserver<BaiduFaceDetectBean>() {
            @Override
            public void onNext(BaiduFaceDetectBean value) {
                if (value != null && value.result != null && value.result.face_list != null && value.result.face_list.size() > 0) {
                    intent.putExtra("gender",value.result.face_list.get(0).gender.type);
//                    intent.putExtra("gender","female");//测试
                    setResult(100,intent);
                    finish();
                    if(value.result.face_list.get(0).gender.type.equals("female")){//判断为女性需要在走一个接口
                    getUpdateAuthentication();
                    }
                }else {
                    ToastUtils.showLong("获取性别失败！");
                    setResult(100);
                    finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showLong(e.getMessage());
            }
            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
    public void  getUpdateAuthentication(){
        HttpClient.getApi().getUpdateAuthentication(SPUtils.getInstance().getUserInfo().getUserPhone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull BaseResponse getBaiDUTokenBeanBaseResponse) {

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
