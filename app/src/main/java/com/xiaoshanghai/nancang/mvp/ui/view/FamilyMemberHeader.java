package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyMemberHeader extends RelativeLayout {

    @BindView(R.id.iv_family_logo)
    CircleImageView ivFamilyLogo;
    @BindView(R.id.iv_family_bg)
    ImageView ivFamilyBg;
    @BindView(R.id.tv_family_name)
    TextView tvFamilyName;
    @BindView(R.id.tv_family_id)
    TextView tvFamilyId;
    @BindView(R.id.tv_family_num)
    TextView tvFamilyNum;
    @BindView(R.id.ll_family_num)
    LinearLayout llFamilyNum;
    @BindView(R.id.tv_family_members)
    TextView tvFamilyMembers;

    public FamilyMemberHeader(Context context) {
        super(context);
        init();
    }

    public FamilyMemberHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FamilyMemberHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_family_member_heard, this, true);
        ButterKnife.bind(view);
    }

    public void upData(ClanResult result, int status) {

        if (status == 0) {

            llFamilyNum.setVisibility(GONE);

        } else if (status == 1) {

            llFamilyNum.setVisibility(VISIBLE);

        }

        GlideAppUtil.loadImage(getContext(), result.getClanPicture(), ivFamilyBg);
        GlideAppUtil.loadImage(getContext(), result.getClanPicture(), ivFamilyLogo);
        tvFamilyName.setText(result.getClanName());
        tvFamilyId.setText(result.getClanNumber() + "");
        tvFamilyNum.setText(result.getMember() + "");
        tvFamilyMembers.setText(result.getMember() + "");


    }


}
