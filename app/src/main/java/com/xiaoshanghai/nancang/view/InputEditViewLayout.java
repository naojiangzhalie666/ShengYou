package com.xiaoshanghai.nancang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.xiaoshanghai.nancang.R;

/**
 * @date :2019/8/29 0029
 * @author : gaoxiaoxiong
 * @description: 密码框的输入
 **/
public class InputEditViewLayout extends androidx.appcompat.widget.AppCompatEditText implements View.OnKeyListener {
    private String TAG = InputEditViewLayout.class.getSimpleName();
    private Paint mPaint;
    // 一个密码所占的宽度
    private int mPasswordItemWidth;
    // 密码的个数默认为6位数
    private int mPasswordNumber = 6;
    // 背景边框颜色
    private int mBgColor = Color.parseColor("#999999");
    // 背景边框大小
    private int mBgSize = 1;
    // 背景边框圆角大小
    private int mBgCorner = 0;
    // 密码圆点的颜色
    private int mPasswordColor = mBgColor;
    // 密码圆点的半径大小
    private int mPasswordRadius = 4;
    private int itemDisTance = 0;//Item之间的间隔
    private int paddingTop = dip2px(10);
    private boolean isPassWord = true;//是否为密码模式，如果不是密码模式，就明文展示
    private int iTextSise = 14;
    private boolean isHaveNotice = false;//判断是否已经回调给前面了
    public InputEditViewLayout(Context context) {
        this(context, null);
    }

    public InputEditViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private OnInputPassworEditListener onInputPassworEditListener;

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == KeyEvent.KEYCODE_DEL) {
            isHaveNotice = false;
        }
        return false;
    }


    public interface OnInputPassworEditListener {
        void onFinishInput();
    }

    public void setOnInputPassworEditListener(OnInputPassworEditListener onInputPassworEditListener) {
        this.onInputPassworEditListener = onInputPassworEditListener;
    }

    public InputEditViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化一些布局必须参数
        initAttributeSet(context, attrs);
        //初始化画笔 + 个数计算
        init();
        // 设置输入模式是密码
        if (isPassWord){
            setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD|EditorInfo.TYPE_CLASS_NUMBER);
        }else {
            setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        }
        // 不显示光标
        setCursorVisible(false);
        //获取焦点
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        setOnKeyListener(this);

    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputEditViewLayout);
        mBgSize = typedArray.getInteger(R.styleable.InputEditViewLayout_bgSize, 1);
        mPasswordNumber = typedArray.getInteger(R.styleable.InputEditViewLayout_passwordNumber, 6);
        mBgCorner = typedArray.getInteger(R.styleable.InputEditViewLayout_bgCorner, 5);
        mPasswordRadius = typedArray.getInteger(R.styleable.InputEditViewLayout_passwordRadius, 4);
        mPasswordColor = typedArray.getColor(R.styleable.InputEditViewLayout_passwordColor, mBgColor);
        mBgColor = typedArray.getColor(R.styleable.InputEditViewLayout_et_bgColor, mBgColor);
        isPassWord = typedArray.getBoolean(R.styleable.InputEditViewLayout_isPassword, true);
        iTextSise = typedArray.getInteger(R.styleable.InputEditViewLayout_iTextSise,16);
        typedArray.recycle();
        mBgSize = dip2px(mBgSize);
        mBgCorner = dip2px(mBgCorner);
        mPasswordRadius = dip2px(mPasswordRadius);
        iTextSise = sp2px(context,iTextSise);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mPasswordNumber)});
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mBgColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPasswordItemWidth = getMeasuredHeight() - paddingTop;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemDisTance = (getWidth() - (mPasswordNumber * mPasswordItemWidth)) / (mPasswordNumber + 1);//平均下来的距离
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        drawBg(itemDisTance, canvas);
        drawHidePassword(canvas);
    }

    /**
     * @date :2019/8/29 0029
     * @author : gaoxiaoxiong
     * @description:绘制背景
     **/
    private void drawBg(int startX, Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBgColor);
        mPaint.setStrokeWidth(mBgSize);
        for (int i = 0; i < mPasswordNumber; i++) {
            RectF rectF = new RectF(startX, paddingTop / 2, startX + mPasswordItemWidth, mPasswordItemWidth + paddingTop / 2);
            canvas.drawRoundRect(rectF, mBgCorner, mBgCorner, mPaint);
            startX = startX + mPasswordItemWidth + itemDisTance;
        }

    }

    /**
     * @date :2019/8/29 0029
     * @author : gaoxiaoxiong
     * @description:绘制密码
     **/

    private void drawHidePassword(Canvas canvas) {
        int passwordLength = getText().toString().trim().length();
        mPaint.setColor(mPasswordColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (!isPassWord) {//不是密码模式
            mPaint.setTextSize(iTextSise);
            String text = getText().toString();//1  2  3
            for (int i = 0; i < passwordLength; i++) {
                int cx = itemDisTance + i * mPasswordItemWidth + mPasswordItemWidth / 2 + i * itemDisTance;
                String aaa = text.substring(i, i + 1);
                //文字处理
                TextPaint paint = this.getPaint();
                //计算baseLine
                int center = getMeasuredHeight() / 2 + paddingTop / 2;
                int baseLine = (int) (center + (paint.getFontMetrics().bottom - paint.getFontMetrics().top) / 2 - paint.getFontMetrics().bottom);
                int fontWidth = (int) mPaint.measureText(aaa);
                canvas.drawText(aaa, cx - fontWidth / 2, baseLine, mPaint);
            }
        } else {
            //密码处理
            for (int i = 0; i < passwordLength; i++) {
                int cx = itemDisTance + i * mPasswordItemWidth + mPasswordItemWidth / 2 + i * itemDisTance;
                canvas.drawCircle(cx, getHeight() / 2, mPasswordRadius, mPaint);
            }
        }

        if (passwordLength == mPasswordNumber && !isHaveNotice) {
            if (onInputPassworEditListener != null) {
                onInputPassworEditListener.onFinishInput();
                isHaveNotice = true;
            }
        }
    }

    /**
     * @date :2019/9/24 0024
     * @author : gaoxiaoxiong
     * @description:清除数据
     **/
    public void cleanData(){
        this.setText("");
        isHaveNotice = false;
    }

    /**
     * dip 转 px
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }



    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}