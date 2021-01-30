package com.xiaoshanghai.nancang.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SelfIntroductionAct extends BaseMvpActivity implements TitleBarClickCallback {


    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_nick_num)
    TextView tvNickNum;

    private MineReslut reslut;

    @Override
    public int setLayoutId() {
        return R.layout.activity_self_introduction;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleBar.setOnClickCallback(this);

        Bundle extras = getIntent().getExtras();
        reslut = (MineReslut) extras.getSerializable(Constant.MINE_RESLUT);
        etNickname.setText(reslut.getUserIntroduce());


    }

    @OnTextChanged(R.id.et_nickname)
    void onTextChanged(Editable editable) {
        ivDelete.setVisibility(etNickname.length() > 0 ? View.VISIBLE : View.GONE);
        tvNickNum.setText(etNickname.length() + "");
    }


    @OnClick({R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                etNickname.setText("");
                break;
        }
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        if (!TextUtils.isEmpty(etNickname.getText().toString())) {
            saveNickName(etNickname.getText().toString().trim());
        } else {
            ToastUtils.showShort("请输入个人介绍");
        }
    }

    private void saveNickName(String self){
        showLoading();
        HttpClient.getApi().editIntroduce(self)
                .execOnThread(getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        hideLoading();
                        if (bean.equals("1")){
                            setResult(RESULT_OK,new Intent().putExtra(Constant.SELF,self));
                            finish();
                        } else {
                            ToastUtils.showShort("修改失败");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        hideLoading();
                        ToastUtils.showShort(msg);
                    }
                });
    }
}
