package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HeadgearAct;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.xiaoshanghai.nancang.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarRecordFoot extends RelativeLayout {

    @BindView(R.id.tv_give_away)
    TextView tvGiveAway;

    private boolean isSelf;

    private MineReslut mResult;

    public CarRecordFoot(Context context) {
        this(context, null);

    }

    public CarRecordFoot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarRecordFoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_car_record, this, true);
        ButterKnife.bind(view);
    }


    @OnClick(R.id.rl_give_away)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MINE_RESLUT,mResult);
        ActStartUtils.startAct(getContext(), HeadgearAct.class,bundle);
    }

    public void setMineResult(MineReslut result) {

        this.mResult = result;

        if (result.getId().equals(SPUtils.getInstance().getUserInfo().getId())) {
            isSelf = true;
            tvGiveAway.setText("立即购买");
        } else {
            isSelf = false;
            tvGiveAway.setText("送TA一部");
        }
    }

}
