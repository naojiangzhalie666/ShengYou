package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.RoomResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.ScreenUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class HomeRadioAdapter extends BaseQuickAdapter<RoomResult, HomeRadioAdapter.HomeRadioHolder> {


    public HomeRadioAdapter() {
        super(R.layout.adapter_home_radio);
    }

    @Override
    protected void convert(@NotNull HomeRadioHolder holder, RoomResult s) {


        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        width = width / 30;

        int length = s.getRoomTypeName().trim().length();
        if (s.getRoomTypeName() != null&&s.getRoomTypeColor()!=null) {
            Drawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width((length + 1) * width)  // width in px
                    .height(ScreenUtils.dp2px(getContext(), 18)) // height in px
                    .textColor(Color.parseColor("#ffffff"))
                    .fontSize(ScreenUtils.dp2px(getContext(), 12))
                    .endConfig()
                    .buildRoundRect(s.getRoomTypeName(), Color.parseColor(s.getRoomTypeColor()), ScreenUtils.dp2px(getContext(), 10));

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        setTextStyle(s.getRoomName(), drawable, holder.mTvName);
        }
        GlideAppUtil.loadImage(getContext(), s.getUserPictureUrl(), holder.mCivRoom);


        Glide.with(getContext()).asGif().load(R.mipmap.icon_home_flag).into(holder.mIvFlag);
        holder.mTvNum.setText(s.getPersonCount() + "");

    }


    /**
     * 利用SpannableString实现添加Drawable
     *
     * @param content
     * @param drawable
     * @param tv
     */
    private void setTextStyle(String content, Drawable drawable, TextView tv) {
        SpannableString spanText = new SpannableString(" " + " " + " " + content);
        // 替换0,1的字符
        if (drawable != null) {
            spanText.setSpan(new ImageSpan(drawable), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spanText.setSpan("", 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanText);
    }


    static class HomeRadioHolder extends BaseViewHolder {

        public TextView mTvName;
        public CircleImageView mCivRoom;
        public ImageView mIvFlag;
        public TextView mTvNum;


        public HomeRadioHolder(@NotNull View view) {
            super(view);
            mTvName = view.findViewById(R.id.tv_room_name);
            mCivRoom = view.findViewById(R.id.civ_room);
            mIvFlag = view.findViewById(R.id.iv_flag);
            mTvNum = view.findViewById(R.id.tv_num);
        }
    }
}
