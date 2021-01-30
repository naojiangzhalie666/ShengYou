package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.ScreenUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvatarView extends RelativeLayout {

    @BindView(R.id.rl_avatar)
    RelativeLayout mRlAvatar;
    @BindView(R.id.iv_avater)
    CircleImageView ivAvater;
    @BindView(R.id.iv_pf)
    ImageView ivPf;

    private int height;
    private int width;

    private Context mContext;

    private View mView;

    private int dimension;

    private int fixed;

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.view_avatar, this, true);

        ButterKnife.bind(mView);

        height = getViewHeight(attrs, context);
        width = getViewWidth(attrs, context);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.Avatar);


        dimension = (int) typedArray.getDimension(R.styleable.Avatar_headwearPadding, 0f);
        fixed = (int) typedArray.getDimension(R.styleable.Avatar_fixed_padding, 0f);

        float radius = typedArray.getDimension(R.styleable.Avatar_avatar_radius, 0);
        ivAvater.setRadius(radius);

        int resourceId = typedArray.getResourceId(R.styleable.Avatar_def_avatar, 0);
        setDefAvatar(resourceId);

        int def_headwear = typedArray.getResourceId(R.styleable.Avatar_def_headwear, 0);
        setDefHeadwear(def_headwear);


    }

    /**
     * 获取控件高度
     *
     * @param attributeSet
     * @param context
     * @return
     */
    public int getViewHeight(AttributeSet attributeSet, Context context) {
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
    public int getViewWidth(AttributeSet attributeSet, Context context) {
        String layout_width = context.getString(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "layout_width", 0));
        int width = Integer.parseInt(layout_width.substring(0, layout_width.indexOf(".")));
        return ScreenUtils.dp2px(context, width);
    }

    private void setDefAvatar(int resourceId) {
        if (resourceId != 0) {
            ivAvater.setImageDrawable(getResources().getDrawable(resourceId));
        } else {
            ivAvater.setImageDrawable(null);
        }
    }

    private void setDefHeadwear(int resourceId) {
        if (resourceId != 0) {
            ivPf.setImageDrawable(getResources().getDrawable(resourceId));
        } else {
            ivPf.setImageDrawable(null);
        }
    }

    public void setAvatarAndHeadear(String avatar, String headwear) {

        ViewGroup.LayoutParams lp = ivAvater.getLayoutParams();

        if (fixed > 0) {
            lp.height = height - fixed;
            lp.width = width - fixed;

        } else {

            if (TextUtils.isEmpty(headwear)) {

                lp.height = height - ScreenUtils.dp2px(getContext(), 5);
                lp.width = width - ScreenUtils.dp2px(getContext(), 5);
            } else {
                lp.height = height - dimension;
                lp.width = width - dimension;
            }

        }




        ivAvater.setLayoutParams(lp);

        if (!TextUtils.isEmpty(avatar)) {
            GlideAppUtil.loadImage(getContext(), avatar, ivAvater);
        }

        if (!TextUtils.isEmpty(headwear)) {
            GlideAppUtil.loadImage(getContext(), headwear, ivPf);
        } else {
            ivPf.setImageDrawable(null);
        }


    }

    public void setDefAvatarAndHeadear(int avatarResource, int headwearResource) {
        ViewGroup.LayoutParams lp = ivAvater.getLayoutParams();


        if (fixed > 0) {
            lp.height = height - fixed;
            lp.width = width - fixed;

        } else {
            if (headwearResource == 0) {
                lp.height = height - ScreenUtils.dp2px(getContext(), 5);
                lp.width = width - ScreenUtils.dp2px(getContext(), 5);
            } else {
                lp.height = height - dimension;
                lp.width = width - dimension;
            }

        }

        ivAvater.setLayoutParams(lp);

        setDefAvatar(avatarResource);

        setDefHeadwear(headwearResource);

    }


}
