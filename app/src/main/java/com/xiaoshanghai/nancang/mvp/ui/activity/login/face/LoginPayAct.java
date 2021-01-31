package com.xiaoshanghai.nancang.mvp.ui.activity.login.face;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseActivity;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.bean.GetAppPayBuyTicketBean;
import com.xiaoshanghai.nancang.net.bean.PayResult;
import java.util.Map;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
public class LoginPayAct extends BaseActivity {
    @Override
    public int setLayoutId() {
        return R.layout.loginpayact;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.tvPay).setOnClickListener(v -> {//支付
            postPay();
        });
    }
 public void  postPay(){
     HttpClient.getApi().getAppPayBuyTicket("1",
             "1")
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new Observer<GetAppPayBuyTicketBean>() {
                 @Override
                 public void onSubscribe(@NonNull Disposable d) {
                 }
                 @Override
                 public void onNext(@NonNull GetAppPayBuyTicketBean payBean) {
                     payZFB(payBean.getData().getOrderString());
                 }
                 @Override
                 public void onError(@NonNull Throwable e) {
                     setResult(50,new Intent().putExtra("payStatus","fail"));
                     finish();
                 }
                 @Override
                 public void onComplete() {

                 }
             });

 }
        public  void  payZFB(String orderString){
            final Runnable payRunnable = () -> {
                PayTask alipay = new PayTask(this);
                Map<String, String> result = alipay.payV2(orderString, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        setResult(50,new Intent().putExtra("payStatus","success"));
                        finish();
                    } else {
                        setResult(50,new Intent().putExtra("payStatus","fail"));
                        finish();
                    }
                    break;
                }
                default:
                    setResult(50,new Intent().putExtra("payStatus","fail"));
                    finish();
                    break;
            }
        }
    };
}
