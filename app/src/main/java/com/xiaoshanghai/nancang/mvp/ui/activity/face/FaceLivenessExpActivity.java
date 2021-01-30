package com.xiaoshanghai.nancang.mvp.ui.activity.face;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiaoshanghai.nancang.bean.BaiduFaceDetectBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FaceLivenessExpActivity extends FaceLivenessActivity {

    private AlertDialog mDefaultDialog;
    private HashMap<String, String> base64ImageMap;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
//            showMessageDialog("人脸识别", "识别成功");
//            this.base64ImageMap = base64ImageMap;
            Intent intent = new Intent();
            if (base64ImageMap != null) {
                String image = base64ImageMap.get("bestImage0");
                intent.putExtra("image", image);
            }
            setResult(RESULT_OK, intent);
            finish();
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
//            showMessageDialog("人脸识别", "采集超时");
            ToastUtils.showLong("采集超时");
            finish();
        }
    }


    private void showMessageDialog(String title, String message) {
        if (mDefaultDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title).
                    setMessage(message).
                    setNegativeButton("确认",
                            (dialog, which) -> {
                                mDefaultDialog.dismiss();
                                Intent intent = new Intent();
                                if (base64ImageMap != null) {
                                    String image = base64ImageMap.get("bestImage0");
                                    intent.putExtra("image", image);
                                }
                                setResult(RESULT_OK, intent);
                                finish();
                            });
            mDefaultDialog = builder.create();
            mDefaultDialog.setCancelable(true);
        }
        mDefaultDialog.dismiss();
        mDefaultDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    String image = data.getStringExtra("image");
//                    faceBeautyMatch(image);
                }
                break;
        }
    }
    private void faceBeautyMatch(String image) {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";

        final Observable<BaiduFaceDetectBean> observable = Observable.create(new ObservableOnSubscribe<BaiduFaceDetectBean>() {

            @Override
            public void subscribe(ObservableEmitter<BaiduFaceDetectBean> e) throws Exception {
                Map<String, Object> map = new HashMap<>();
                map.put("image", image);
                map.put("face_field", "beauty,gender");
                map.put("image_type", "BASE64");
                map.put("face_type", "LIVE");
                String param = GsonUtils.toJson(map);
//                String accessToken = mBaiduAiToken;
//                String result = HttpUtil.post(url, accessToken, "application/json", param);
//                BaiduFaceDetectBean entity = new Gson().fromJson(result, BaiduFaceDetectBean.class);
//                e.onNext(entity);
                e.onComplete();
            }

        });
        DisposableObserver<BaiduFaceDetectBean> disposableObserver = new DisposableObserver<BaiduFaceDetectBean>() {

            @Override
            public void onNext(BaiduFaceDetectBean value) {
                if (value != null && value.result != null && value.result.face_list != null && value.result.face_list.size() > 0) {
                    int beauty = (int) value.result.face_list.get(0).beauty;
//                    Toast.makeText(AccountLoginActivity.this,String.valueOf(beauty),Toast.LENGTH_SHORT).show();
//                    sendFaceBeauty(beauty);
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
}