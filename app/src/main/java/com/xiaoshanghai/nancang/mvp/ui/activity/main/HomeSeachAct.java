package com.xiaoshanghai.nancang.mvp.ui.activity.main;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.helper.EnterRoomHelp;
import com.xiaoshanghai.nancang.mvp.contract.HomeSeachContract;
import com.xiaoshanghai.nancang.mvp.presenter.HomeSeachPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.EnterRoomRecordAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeSeachAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.SeachAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.EmptyView;
import com.xiaoshanghai.nancang.net.bean.HomeSeachEntity;
import com.xiaoshanghai.nancang.net.bean.SeachRoomEntty;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class HomeSeachAct extends BaseMvpActivity<HomeSeachPresenter> implements HomeSeachContract.View {

    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.iv_clear_seach)
    ImageView ivClearSeach;
    @BindView(R.id.et_seach)
    EditText etSeach;
    @BindView(R.id.tv_seach)
    TextView tvSeach;
    @BindView(R.id.rcy_seach_record)
    RecyclerView rcySeachRecord;
    @BindView(R.id.rcy_enter_record)
    RecyclerView rcyEnterRecord;
    @BindView(R.id.group_seach_record)
    Group groupSeachRecord;
    @BindView(R.id.group_enter_room)
    Group groupEnterRoom;
    @BindView(R.id.rcy_seach_result)
    RecyclerView rcySeachResult;


    private boolean isUserList = false;

    private int isSeachRecord = 0;
    private int isEnterRoom = 0;

    private HomeSeachAdapter mSeachAdapter = new HomeSeachAdapter();

    private EnterRoomRecordAdapter mEnterRooomAdapter = new EnterRoomRecordAdapter();

    private SeachAdapter mSeachUserAdapter = new SeachAdapter();


    @Override
    public int setLayoutId() {
        return R.layout.activity_home_seach;
    }

    @Override
    protected HomeSeachPresenter createPresenter() {
        return new HomeSeachPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        initEditText();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getSeachRoomRecord();
        initSeachRecord();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcySeachRecord.setLayoutManager(linearLayoutManager);
        rcySeachRecord.setAdapter(mSeachAdapter);

        mSeachAdapter.setOnItemClickListener((adapter, view, position) -> {
            String seachContent = mSeachAdapter.getItem(position);
            etSeach.setText(seachContent);
            etSeach.setSelection(etSeach.getText().length());

            String seach = SPUtils.getInstance().getString(SpConstant.HOME_SEACH_KEY);
            if (TextUtils.isEmpty(seach)) {
                SPUtils.getInstance().put(SpConstant.HOME_SEACH_KEY, etSeach.getText().toString().trim());
            } else {
                SPUtils.getInstance().put(SpConstant.HOME_SEACH_KEY, etSeach.getText().toString().trim() + "," + seach);
            }

            mSeachUserAdapter.setList(null);

            mPresenter.seach(etSeach.getText().toString().trim());
            isUserList = true;
            showView(isUserList, isSeachRecord, isEnterRoom);
        });

        LinearLayoutManager enterManager = new LinearLayoutManager(this);
        enterManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcyEnterRecord.setLayoutManager(enterManager);
        rcyEnterRecord.setAdapter(mEnterRooomAdapter);

        mEnterRooomAdapter.setOnItemClickListener((adapter, view, position) -> {
            SeachRoomEntty item = mEnterRooomAdapter.getItem(position);
            String roomId = item.getRoomId();
            EnterRoomHelp.enterRoomControl(HomeSeachAct.this,this,roomId);
        });

        LinearLayoutManager seachLayout = new LinearLayoutManager(this);
//        seachLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcySeachResult.setLayoutManager(seachLayout);
        rcySeachResult.setAdapter(mSeachUserAdapter);

        mSeachUserAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.USER_ID, mSeachUserAdapter.getItem(position).getId());
            ActStartUtils.startAct(this, HomePageAct.class, bundle);
            finish();
        });

    }

    private void initSeachRecord() {
        String seachRecordStr = SPUtils.getInstance().getStr(SpConstant.HOME_SEACH_KEY);
        String[] seachRecord = null;
        if (!TextUtils.isEmpty(seachRecordStr)) {
            seachRecord = seachRecordStr.split(",");
        }

        if (seachRecord == null || seachRecord.length <= 0) {
            isSeachRecord = 0;
            showView(isUserList, isSeachRecord, isEnterRoom);
        } else {
            isSeachRecord = 1;
            List<String> strings = Arrays.asList(seachRecord);
            mSeachAdapter.setList(strings);
            showView(isUserList, isSeachRecord, isEnterRoom);
        }
    }


    private void initEditText() {
        etSeach.setFocusable(true);
        etSeach.setFocusableInTouchMode(true);
        etSeach.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        etSeach.setOnEditorActionListener((v, actionId, event) -> {

            //以下方法防止两次发送请求
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                        //发送请求
                        if (etSeach.length() > 0) {
                            String seach = SPUtils.getInstance().getString(SpConstant.HOME_SEACH_KEY);
                            if (TextUtils.isEmpty(seach)) {
                                SPUtils.getInstance().put(SpConstant.HOME_SEACH_KEY, etSeach.getText().toString().trim());
                            } else {
                                SPUtils.getInstance().put(SpConstant.HOME_SEACH_KEY, etSeach.getText().toString().trim() + "," + seach);
                            }
                            mSeachUserAdapter.setList(null);

                            mPresenter.seach(etSeach.getText().toString().trim());
                            isUserList = true;
                            showView(isUserList, isSeachRecord, isEnterRoom);

                        }
                        return true;
                    default:
                        return true;
                }
            }


            return false;

        });
    }

    @OnTextChanged(R.id.et_seach)
    public void onTextChanged(Editable editable) {
        if (etSeach.length() > 0) {
            ivClearSeach.setVisibility(View.VISIBLE);
        } else {
            isUserList = false;
            ivClearSeach.setVisibility(View.GONE);
            initSeachRecord();
            showView(isUserList, isSeachRecord, isEnterRoom);
        }
    }

    @OnClick({R.id.iv_black, R.id.iv_clear_seach, R.id.tv_seach, R.id.iv_delete_seach_record, R.id.iv_delete_enter_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                finish();
                break;
            case R.id.iv_clear_seach:
                etSeach.setText("");
                break;
            case R.id.tv_seach:
                if (etSeach.length() > 0) {
                    String seach = SPUtils.getInstance().getString(SpConstant.HOME_SEACH_KEY);
                    if (TextUtils.isEmpty(seach)) {
                        SPUtils.getInstance().put(SpConstant.HOME_SEACH_KEY, etSeach.getText().toString().trim());
                    } else {
                        SPUtils.getInstance().put(SpConstant.HOME_SEACH_KEY, etSeach.getText().toString().trim() + "," + seach);
                    }
                    mSeachUserAdapter.setList(null);
                    mPresenter.seach(etSeach.getText().toString().trim());
                    isUserList = true;
                    showView(isUserList, isSeachRecord, isEnterRoom);
                }
                break;
            case R.id.iv_delete_seach_record:
                SPUtils.getInstance().remove(SpConstant.HOME_SEACH_KEY);
                isSeachRecord = 0;
                initSeachRecord();
                break;
            case R.id.iv_delete_enter_record:
                mPresenter.removeEnterRoomRecord(SPUtils.getInstance().getUserInfo().getId());
                break;
        }
    }

    private void showView(boolean isUserList, int isSeachRecord, int isEnterRoom) {

        if (isUserList) {

            rcySeachResult.setVisibility(View.VISIBLE);

            groupSeachRecord.setVisibility(View.GONE);
            groupEnterRoom.setVisibility(View.GONE);

        } else {
            rcySeachResult.setVisibility(View.GONE);

            if (isSeachRecord == 0) {
                groupSeachRecord.setVisibility(View.GONE);
            } else if (isSeachRecord == 1) {
                groupSeachRecord.setVisibility(View.VISIBLE);
            }

            if (isEnterRoom == 0) {
                groupEnterRoom.setVisibility(View.GONE);
            } else if (isEnterRoom == 1) {
                groupEnterRoom.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onRecordResult(List<SeachRoomEntty> result) {
        if (result == null || result.size() <= 0) {
            isEnterRoom = 0;
        } else {
            isEnterRoom = 1;
        }
        mEnterRooomAdapter.setList(result);

        showView(isUserList, isSeachRecord, isEnterRoom);
    }

    @Override
    public void onDeleteSuccess(String status) {
        if (status.equals("1")) {
            isEnterRoom = 0;
            mPresenter.getSeachRoomRecord();
        } else {
            ToastUtils.showShort("删除失败");
        }
    }

    @Override
    public void onSeachSuccess(List<HomeSeachEntity> seachResult) {

        if (seachResult != null && seachResult.size() > 0) {

            mSeachUserAdapter.setList(seachResult);
        } else {

            EmptyView emptyView = new EmptyView(this);
            emptyView.setEmptyImgAndEmptyText(R.mipmap.img_search_empty, "在这里什么也没找到");
            mSeachUserAdapter.setEmptyView(emptyView);
        }

    }

    @Override
    public void onSeachError(String msg) {

    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}