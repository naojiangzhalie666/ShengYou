package com.xiaoshanghai.nancang.mvp.ui.activity.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.EventConstant;
import com.xiaoshanghai.nancang.helper.EnterRoomHelp;
import com.xiaoshanghai.nancang.mvp.contract.MainContract;
import com.xiaoshanghai.nancang.mvp.presenter.MainPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.TeensAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.square.GraphicReleaseAct;
import com.xiaoshanghai.nancang.mvp.ui.fragment.home.HomeFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.MineFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.MsgFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.square.SquareFragment;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.bean.BackstageRoom;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.Event;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.EventBusUtil;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//根Activity
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbSquare)
    RadioButton rbSquare;
    @BindView(R.id.rbMsg)
    RadioButton rbMsg;
    @BindView(R.id.rbMine)
    RadioButton rbMine;

    private HomeFragment mHomeFragment;
    private SquareFragment mSquareFragment;
    private MsgFragment mMsgFragment;
    private MineFragment mMineFragment;

    private BaseMvpFragment baseFragment;

    public static String mRoomId = "";

    public static String mRoomBg = "";


    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        if (event != null) {
            int code = event.getCode();
            if (code == EventConstant.ENTER_HI_CHAT_ROOM) {
                EnterRoomHelp.enterRoomControl(this, this, event.getData().toString());
            }

            if (code == EventConstant.BACKSTAGE_ROOM) {
                BackstageRoom data = (BackstageRoom) event.getData();
                mRoomId = data.getRoomId();
                mRoomBg = data.getRoomBg();

                Boolean show = EasyFloat.Companion.isShow(this);
                if (show) {
                    EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");
                }

            }

            if (code == EventConstant.OUT_ROOM) {
                BackstageRoom data = (BackstageRoom) event.getData();
                if (!TextUtils.isEmpty(mRoomId) && mRoomId.equals(data.getRoomId())) {
                    mRoomId = "";
                    mRoomBg = "";
                    mPresenter.exitRoom(mRoomId);
                    TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(MainActivity.this);
                    trtcVoiceRoom.exitRoom(null);
                    EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");
                }
            }

            if (code == EventConstant.FLOAT_DISMISS) {
                mRoomId = "";
                mRoomBg = "";
                EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");

            }

            if (code == EventConstant.ROOM_DESTROY) {
                mRoomId = "";
                mRoomBg = "";
                EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");
            }
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        UserBean userInfo = SPUtils.getInstance().getUserInfo();
        if (userInfo != null) {
            EventBusUtil.sendEvent(new Event(EventConstant.LOGIN_SUCCESS));
        }
//        mHomeFragment = new HomeFragment();
        mSquareFragment = new SquareFragment();
        replace(mSquareFragment);

//        mPresenter.getTeensStatus(SPUtils.getInstance().getUserInfo().getId());
        getAppTopic();
    }

    private void showEasyFloat(String photoUrl) {

        EasyFloat.Companion.with(this)
                .setTag("MainActivity")
                // 设置浮窗xml布局文件，并可设置详细信息
                .setLayout(R.layout.dialog_small_window, view -> {

                    ImageView mIv = view.findViewById(R.id.iv_dismiss);

                    ImageView mIvPhoto = view.findViewById(R.id.iv_photo);
                    GlideAppUtil.loadImage(this, photoUrl, mIvPhoto, R.mipmap.icon_default_avatar);

                    view.setOnClickListener(view1 -> {
                        EnterRoomHelp.enterRoomControl(this, this, mRoomId);
                    });

                    mIv.setOnClickListener(view12 -> {
                        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(MainActivity.this);
                        trtcVoiceRoom.exitRoom((code, msg) -> {
                            if (code == 0 || code == 10009) {
                                mPresenter.exitRoom(mRoomId);
                                mRoomId = "";
                                EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");
                            }
                        });

                    });
                })
                .setShowPattern(ShowPattern.CURRENT_ACTIVITY)
                .setSidePattern(SidePattern.DEFAULT)
                .setGravity(Gravity.END | Gravity.BOTTOM, 0, -100)
                .show();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mRoomId) && !TextUtils.isEmpty(mRoomBg)) {
            EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");
            showEasyFloat(mRoomBg);
        }
    }

    /**
     * 替换页面
     *
     * @param fragment
     */
    private void replace(BaseMvpFragment fragment) {
        if (fragment == baseFragment) return;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            if (baseFragment == null) {
                fragmentTransaction.add(R.id.flContainer, fragment).show(fragment);
            } else if (baseFragment != fragment) {
                fragmentTransaction.add(R.id.flContainer, fragment).hide(baseFragment).show(fragment);
            }
        } else {
            fragmentTransaction.hide(baseFragment).show(fragment);
        }
        baseFragment = fragment;
        fragmentTransaction.commit();
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

    @OnClick({R.id.rbHome, R.id.rbSquare, R.id.rbMsg, R.id.rbMine,R.id.rbAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbHome:
                if (mSquareFragment == null)
                    mSquareFragment = new SquareFragment();
                replace(mSquareFragment);
                break;
            case R.id.rbSquare:
                if (mHomeFragment == null)
                    mHomeFragment = new HomeFragment();
                replace(mHomeFragment);
                break;
            case R.id.rbMsg:
                if (mMsgFragment == null)
                    mMsgFragment = new MsgFragment();
                replace(mMsgFragment);
                break;
            case R.id.rbMine:
                if (mMineFragment == null)
                    mMineFragment = new MineFragment();
                replace(mMineFragment);
                break;
            case R.id.rbAdd:
                ActStartUtils.startAct(this, GraphicReleaseAct.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!TextUtils.isEmpty(mRoomId)) {
            mPresenter.exitRoom(mRoomId);
            TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(MainActivity.this);
            trtcVoiceRoom.exitRoom(null);
        }
        EasyFloat.Companion.dismiss(MainActivity.this, "MainActivity");
        if (!TextUtils.isEmpty(mRoomId)) {
            mRoomId = "";
            mRoomBg = "";
        }
    }

    @Override
    public void teenSuccess(String status) {
        if (status.equals("0")) {
            TipsDialog.createDialog(this, R.layout.dialog_teens)
                    .bindClick(R.id.tv_teen, (v, dialog) -> {
                        ActStartUtils.startAct(this, TeensAct.class);
                        dialog.dismiss();
                    })
                    .bindClick(R.id.tv_know, (v, dialog) -> dialog.dismiss())
                    .show();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    public void  getAppTopic(){
        HttpClient.getApi().getAppTopic("1","10",SPUtils.getInstance().getUserInfo().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull BaseResponse<HomeRoomResult<List<FriendsCircleResult>>> getBaiDUTokenBeanBaseResponse) {
                            if(getBaiDUTokenBeanBaseResponse.getData().getRecords().size()==0){
                               String title="";
                        if(SPUtils.getInstance().getUserInfo().getUserSex()!=0){
                            title="你需要发表个帅气的图片~";
                        }else {
                            title="你需要发表⼀个美美哒的图⽚~";
                        }
                                TipsDialog.createDialog(MainActivity.this, R.layout.login_useradd)
                                        .setCancelable(false)
                                        .setCanceledOnTouchOutside(false)
                                        .setText(R.id.tv_title_2,title)
                                        .bindClick(R.id.tv_cancel, (v, dialog) -> {
                                            ActStartUtils.startAct(MainActivity.this, GraphicReleaseAct.class);
                                        }).show();
                            }
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