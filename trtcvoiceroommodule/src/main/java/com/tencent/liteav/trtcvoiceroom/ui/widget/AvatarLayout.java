package com.tencent.liteav.trtcvoiceroom.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.liteav.trtcvoiceroom.R;


public class AvatarLayout extends RelativeLayout {

//
//    CircleImageView mCivImage;
//
//    ImageView ivPf;
//
//
//    private int height;
//
//    private int width;
//
//    private Context mContext;
//
    private View mView;
//
//    private int dimension;

    public AvatarLayout(Context context) {
        this(context,null);
    }

    public AvatarLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AvatarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {

//        mContext = context;

//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mView = inflater.inflate(R.layout.avatar_layout, this, true);

//        mView = LayoutInflater.from(context).inflate(R.layout.avatar_layout, this, true);
        mView =   LayoutInflater.from(context).inflate(R.layout.avatar_layout, this);

//        initView(mView);


//        height = getViewHeight(attrs, context);
//        width = getViewWidth(attrs, context);

//        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AvatarLayout);
//
//
//        dimension = (int) typedArray.getDimension(R.styleable.AvatarLayout_headwearPadding, 0f);
//
//
//        int resourceId = typedArray.getResourceId(R.styleable.AvatarLayout_def_avatar, 0);
//        setDefAvatar(resourceId);
//
//        int def_headwear = typedArray.getResourceId(R.styleable.AvatarLayout_def_headwear, 0);
//        setDefHeadwear(def_headwear);

    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        //获取SingleTouchView所在父布局的中心点
//        ViewGroup mViewGroup = (ViewGroup) getParent();
//        if(null != mViewGroup){
//            height = mViewGroup.getHeight();
//            width = mViewGroup.getWidth();
//        }
//
//    }
//
//    private void initView(View mView) {
//        mCivImage = mView.findViewById(R.id.civ_image);
//        ivPf = mView.findViewById(R.id.iv_pf);
//    }
//
//    /**
//     * 获取控件高度
//     *
//     * @param attributeSet
//     * @param context
//     * @return
//     */
//    public int getViewHeight(AttributeSet attributeSet, Context context) {
//        String layout_height = context.getString(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "layout_height", 0));
//        int height = Integer.parseInt(layout_height.substring(0, layout_height.indexOf(".")));
//        return dp2px(context, height);
//    }
//
//    /**
//     * 获取控件宽度
//     *
//     * @param attributeSet
//     * @param context
//     * @return
//     */
//    public int getViewWidth(AttributeSet attributeSet, Context context) {
//        String layout_width = context.getString(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "layout_width", 0));
//        int width = Integer.parseInt(layout_width.substring(0, layout_width.indexOf(".")));
//        return dp2px(context, width);
//    }
//
//    private void setDefAvatar(int resourceId) {
//        if (resourceId != 0) {
//            mCivImage.setImageDrawable(getResources().getDrawable(resourceId));
//        } else {
//            mCivImage.setImageDrawable(null);
//        }
//    }
//
//    private void setDefHeadwear(int resourceId) {
//        if (resourceId != 0) {
//            ivPf.setImageDrawable(getResources().getDrawable(resourceId));
//        } else {
//            ivPf.setImageDrawable(null);
//        }
//    }
//
//    public void setAvatarAndHeadear(String avatar, String headwear) {
//
//        ViewGroup.LayoutParams lp = mCivImage.getLayoutParams();
//
//        if (TextUtils.isEmpty(headwear)) {
//
//            lp.height = height - dp2px(getContext(), 5);
//            lp.width = width - dp2px(getContext(), 5);
//        } else {
//            lp.height = height - dimension;
//            lp.width = width - dimension;
//        }
//
//
//        mCivImage.setLayoutParams(lp);
//
//        if (!TextUtils.isEmpty(avatar)) {
//            Glide.with(getContext())
//                    .load(avatar)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(mCivImage);
//        }
//
//        if (!TextUtils.isEmpty(headwear)) {
//            Glide.with(getContext())
//                    .load(avatar)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(mCivImage);
//        }
//
//
//    }
//
//    public void setDefAvatarAndHeadear(int avatarResource, int headwearResource) {
//        ViewGroup.LayoutParams lp = mCivImage.getLayoutParams();
//
//        if (headwearResource == 0) {
//
//            lp.height = height - dp2px(getContext(), 5);
//            lp.width = width - dp2px(getContext(), 5);
//        } else {
//            lp.height = height - dimension;
//            lp.width = width - dimension;
//        }
//
//        mCivImage.setLayoutParams(lp);
//
//        setDefAvatar(avatarResource);
//
//        setDefHeadwear(headwearResource);
//
//    }
//
//    /**
//     * convert dp to its equivalent px
//     * <p>
//     * 将dp转换为与之相等的px
//     */
//    public static int dp2px(Context context, float dipValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dipValue * scale + 0.5f);
//    }
}
