package com.xiaoshanghai.nancang.mvp.ui.activity.square;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpActivity;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.FamilyMemberContract;
import com.xiaoshanghai.nancang.mvp.model.FamilyMemberPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.FamilyMemberAdapter;
import com.xiaoshanghai.nancang.mvp.ui.view.FamilyMemberHeader;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.PopupWindowUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.utils.ToastUtils;
import com.xiaoshanghai.nancang.view.TipsDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyMemberAct extends BaseMvpActivity<FamilyMemberPresenter> implements FamilyMemberContract.View {


    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_share)
    ImageView tvShare;
    @BindView(R.id.tv_more)
    ImageView tvMore;
    @BindView(R.id.rc_family_member)
    RecyclerView rcFamilyMember;
    @BindView(R.id.tv_join)
    TextView tvJoin;


    private String familyId;


    private int patriarch = -1;

    private FamilyMemberAdapter mAdapter;

    private FamilyMemberHeader header;

    private PopupWindow mPopupWindow;

    private MyFamilyResult myFamily;

    private ClanResult mResult;

    @Override
    public int setLayoutId() {
        return R.layout.activity_family_member;
    }


    @Override
    protected FamilyMemberPresenter createPresenter() {
        return new FamilyMemberPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        Bundle extras = getIntent().getExtras();
        familyId = extras.getString(Constant.FAMILY_ID);
        myFamily = (MyFamilyResult) extras.getSerializable(Constant.MY_FAMILY);

        header = new FamilyMemberHeader(this);

        mAdapter = new FamilyMemberAdapter();
        rcFamilyMember.setLayoutManager(new LinearLayoutManager(this));
        rcFamilyMember.setAdapter(mAdapter);

        mAdapter.setHeaderView(header);

        if (!TextUtils.isEmpty(familyId)) {
            mPresenter.getClanInfo(familyId);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            MultiItemEntity item = mAdapter.getItem(position);
            String userId = null;
            int itemType = item.getItemType();
            //TODO 点击跳转个人主页
            if (itemType == 0) {
                userId = ((ClanResult) item).getUserId();
            } else if (itemType == 1) {
                userId = ((FamilyMemberResult) item).getId();
            }

            if (!TextUtils.isEmpty(userId)) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.USER_ID, userId);
                ActStartUtils.startAct(this, HomePageAct.class, bundle);
            }


        });

    }

    @Override
    public void clanInfoSuccess(ClanResult result) {

        if (result != null) {
            this.mResult = result;
//            String patriarchID = result.getClanId();

            if (myFamily == null && result.getIsClanMember() != 1) {         //非家族成员
                patriarch = 0;
                showView(patriarch, result);
                ArrayList<ClanResult> list = new ArrayList<>();
                list.add(result);
                mAdapter.setList(list);
            } else {

//                if (myFamily.getClanId().equals(patriarchID)) {  //家族成员
                if (result.getIsClanMember() == 1) {  //家族成员
                    patriarch = 1;
                    showView(patriarch, result);
                    mPresenter.familyMembers(familyId);

                } else {        //非家族成员

                    patriarch = 0;
                    showView(patriarch, result);
                    ArrayList<ClanResult> list = new ArrayList<>();
                    list.add(result);
                    mAdapter.setList(list);

                }
            }

        }
    }

    @Override
    public void familyMembersSuccess(List<FamilyMemberResult> results) {

        mAdapter.setList(results);
    }

    @Override
    public void joinSuccess(String data) {

        //	0已经加入家族，不能重复加入，1加入成功
        if ("0".equals(data)) {
            ToastUtils.showShort("已经加入家族，不能重复加入");

        } else if ("1".equals(data)) {

            ToastUtils.showShort("加入成功");

            patriarch = 1;
            showView(patriarch, mResult);
            mPresenter.familyMembers(familyId);

        } else if ("2".equals(data)){
            ToastUtils.showShort("您当前有正在等待审批的家族，请等待审核结果哦~");
        }
    }

    @Override
    public void outSuccess(String data) {
        //	0族长不能退，2未满7天不能退1退出成功
        if ("0".equals(data)) {
            ToastUtils.showShort("族长不能退");
        } else if ("1".equals(data)) {
            ToastUtils.showShort("退出成功");
            finish();
        } else if ("2".equals(data)) {
            ToastUtils.showShort("未满7天不能退");
        }
    }

    /**
     * 根据状态显示view
     *
     * @param status 状态 0： 非家族成员 1：家族成员
     * @param result 家族信息
     */
    private void showView(int status, ClanResult result) {
        if (status == 0) {
            tvMore.setVisibility(View.GONE);
            tvShare.setVisibility(View.VISIBLE);
            if (result != null) {
                tvTitleName.setText(result.getClanName());
            }
            tvJoin.setVisibility(View.VISIBLE);

        } else if (status == 1) {
            tvMore.setVisibility(View.VISIBLE);
            tvShare.setVisibility(View.VISIBLE);
            tvTitleName.setText(getResources().getString(R.string.my_famliy));
            tvJoin.setVisibility(View.GONE);
        }
        header.upData(result, status);

    }


    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick({R.id.iv_black, R.id.tv_share, R.id.tv_more, R.id.tv_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                finish();
                break;
            case R.id.tv_share:
                TipsDialog.createDialog(this, R.layout.dialog_share)
                        .bindClick(R.id.tv_cancel, (v, dialog) -> {
                            dialog.dismiss();
                        })
                        .bindClick(R.id.tv_share_buddy, (v, dialog) -> {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.BUDDY_KEY, Constant.CLAN);
                            bundle.putSerializable(Constant.CLAN, mResult);
                            ActStartUtils.startAct(this, MyBuddyAct.class, bundle);
                        })
                        .show();
                break;
            case R.id.tv_more:
                showPopupWindow(tvMore);
                break;
            case R.id.tv_join:
                mPresenter.joinFamily(SPUtils.getInstance().getUserInfo().getId(), familyId);

                break;
        }
    }

    private void showPopupWindow(View anchorView) {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // 设置好参数之后再show
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(anchorView, contentView);
        int xOff = 20; // 可以自己调整偏移
        windowPos[0] -= xOff;

        backgroundAlpha((float) 0.4);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((float) 1);
            }
        });
        mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.popup_content_layout;   // 布局ID
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mPresenter.outFamily(SPUtils.getInstance().getUserInfo().getId(), familyId);

            }
        };
        contentView.findViewById(R.id.ll_out_family).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }
}
