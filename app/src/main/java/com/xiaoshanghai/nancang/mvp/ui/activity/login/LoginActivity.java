package com.xiaoshanghai.nancang.mvp.ui.activity.login;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.EventConstant;
import com.xiaoshanghai.nancang.mvp.contract.LoginContract;
import com.xiaoshanghai.nancang.mvp.presenter.LoginPresenter;
import com.xiaoshanghai.nancang.net.bean.Event;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.AppActivityStatusUtil;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.come)
    TextView mCome;
    @BindView(R.id.singin)
    Button singin;
    @BindView(R.id.loginbytel)
    TextView loginbytel;
    @BindView(R.id.yinshi)
    TextView yinshi;
    @BindView(R.id.yhxieyi)
    TextView yhxieyi;
    @BindView(R.id.byweixin)
    ImageView byweixin;


    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        setGradient(mCome);
        AppActivityStatusUtil.logout();
    }

    @OnClick({R.id.singin, R.id.loginbytel, R.id.yinshi, R.id.yhxieyi, R.id.byweixin})
    public void onViewClicked(View view) {



        switch (view.getId()) {
            case R.id.singin:
                mPresenter.oneKeyLogon();
                break;
            case R.id.loginbytel:
                mPresenter.otherLogon();
                break;
            case R.id.yinshi:
//                ToastUtils.showLong("隐私协议");
                mPresenter.privacyAgreement();
//                ActStartUtils.webActStart(this,Constant.PRIVACY);
                break;
            case R.id.yhxieyi:
//                ToastUtils.showLong("用户协议");
                mPresenter.userAgreement();
//                ActStartUtils.webActStart(this,Constant.USER_AGREEMENT);
                break;
            case R.id.byweixin:
                ToastUtils.showLong("微信登录");
                break;
        }
    }

    /**
     * 一键登录 改为 验证码登录
     *
     * @param phoneNumber
     */
    @Override
    public void oneKeyLogon(String phoneNumber) {
        Intent intent = new Intent(this, RegisteAndLoginAct.class);
        startActivity(intent);
    }

    /**
     * 其他手机号登录 改为密码登录
     */
    @Override
    public void otherLogon() {
        startActivity(new Intent(this, AccountLoginAct.class));
    }

    private void setGradient(TextView textView) {
        float endX = textView.getPaint().getTextSize() * textView.getText().length();
        LinearGradient linearGradient = new LinearGradient(0f, 0f, endX, 0f, Color.parseColor("#FF003B"), Color.parseColor("#FE8AA6"), Shader.TileMode.CLAMP);
        textView.getPaint().setShader(linearGradient);
        textView.invalidate();
    }

    /**
     * 微信登录
     */
    @Override
    public void weixinLogon() {

    }

    @Override
    public void privacyAgreement() {
        ActStartUtils.webActStart(this, Constant.PRIVACY);
    }

    @Override
    public void userAgreement() {
        ActStartUtils.webActStart(this, Constant.USER_AGREEMENT);
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        if (event.getCode() == EventConstant.LOGIN_SUCCESS) {
            finish();
        }
    }

    private long lastTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - lastTime > 2000) {
                ToastUtils.showShort(getResources().getString(R.string.exit_tip));
                lastTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
