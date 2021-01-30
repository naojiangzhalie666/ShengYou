package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.utils.DateUtil;
import com.xiaoshanghai.nancang.utils.ScreenUtils;

import java.util.Calendar;
import java.util.Date;

public class ConstellationView extends RelativeLayout {

    ImageView mIvStart;
    
    private Context mContext;
    private View mView;

    public ConstellationView(Context context) {
        this(context, null);
    }

    public ConstellationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConstellationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_constellation, this);
        mIvStart = mView.findViewById(R.id.iv_start);

        int viewHeight = getViewHeight(attrs, context);
        int viewWidth = getViewWidth(attrs, context);

        ViewGroup.LayoutParams lp = mIvStart.getLayoutParams();
        lp.height = viewHeight;
        lp.width = viewWidth;
        mIvStart.setLayoutParams(lp);
    }

    /**
     * 获取控件高度
     *
     * @param attributeSet
     * @param context
     * @return
     */
    private int getViewHeight(AttributeSet attributeSet, Context context) {
        String layout_height = context.getString(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "layout_height", 0));
        int height = Integer.parseInt(layout_height.substring(0, layout_height.indexOf(".")));
        return ScreenUtils.dp2px(context, height);
    }

    /**
     * 获取控件宽度
     *
     * @param attributeSet
     * @param context
     * @return
     */
    private int getViewWidth(AttributeSet attributeSet, Context context) {
        String layout_width = context.getString(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "layout_width", 0));
        int width = Integer.parseInt(layout_width.substring(0, layout_width.indexOf(".")));
        return ScreenUtils.dp2px(context, width);
    }

    /**
     * 设置星座
     * @param date
     */
    public void setStart(String date) {
        selectStar(date);
    }

    /**
     * 选择星座
     * @param strDate
     */
    private void selectStar(String strDate) {
        Date date = DateUtil.stringToDate(strDate, DateUtil.DatePattern.ONLY_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //date类型转成long类型
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        star(month, day);
    }


    /**
     * 通过出生 设置星座
     *
     * @param month
     * @param day
     * @return
     */
    private void star(int month, int day) {
        String star = "";
        if (month == 1 && day >= 20 || month == 2 && day <= 18) {
            star = "水瓶座";
            mIvStart.setImageResource(R.drawable.icon_aquarius);
        }
        if (month == 2 && day >= 19 || month == 3 && day <= 20) {
            star = "双鱼座";
            mIvStart.setImageResource(R.drawable.icon_aquarius);
        }
        if (month == 3 && day >= 21 || month == 4 && day <= 19) {
            star = "白羊座";
            mIvStart.setImageResource(R.drawable.icon_aries);
        }
        if (month == 4 && day >= 20 || month == 5 && day <= 20) {
            star = "金牛座";
            mIvStart.setImageResource(R.drawable.icon_taurus);
        }
        if (month == 5 && day >= 21 || month == 6 && day <= 21) {
            star = "双子座";
            mIvStart.setImageResource(R.drawable.icon_gemini);
        }
        if (month == 6 && day >= 22 || month == 7 && day <= 22) {
            star = "巨蟹座";
            mIvStart.setImageResource(R.drawable.icon_cancer);
        }
        if (month == 7 && day >= 23 || month == 8 && day <= 22) {
            star = "狮子座";
            mIvStart.setImageResource(R.drawable.icon_leo);
        }
        if (month == 8 && day >= 23 || month == 9 && day <= 22) {
            star = "处女座";
            mIvStart.setImageResource(R.drawable.icon_virgo);
        }
        if (month == 9 && day >= 23 || month == 10 && day <= 23) {
            star = "天秤座";
            mIvStart.setImageResource(R.drawable.icon_libra);
        }
        if (month == 10 && day >= 24 || month == 11 && day <= 22) {
            star = "天蝎座";
            mIvStart.setImageResource(R.drawable.icon_scorpio);
        }
        if (month == 11 && day >= 23 || month == 12 && day <= 21) {
            star = "射手座";
            mIvStart.setImageResource(R.drawable.icon_sagittarius);
        }
        if (month == 12 && day >= 22 || month == 1 && day <= 19) {
            star = "摩羯座";
            mIvStart.setImageResource(R.drawable.icon_capricorn);
        }
    }

}
