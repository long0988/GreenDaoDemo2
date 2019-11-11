package com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.greendaodemo2.R;

/**
 * 功能：自定义的风险等级
 */
public class LevelBar extends ViewGroup {
    private int mViewWidth = 0;//view的宽度
    private int mViewHeight = 0;//view的高度
    private int leftAndRightMargin = 10;//左右两边的间距,可以用户自定义
    private int mLevelBarHeight = 30;//等级条的高度
    //创建一个画笔
    private Paint mPaint = new Paint();

    //初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }
    public LevelBar(Context context) {
        this(context,null);
    }

    public LevelBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context,attrs);
    }
    public LevelBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context,attrs);
    }


    private void initAttributes(Context context, AttributeSet attrs) {

        setBackgroundDrawable(new BitmapDrawable());

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RiskLevelBar);
        leftAndRightMargin = a.getDimensionPixelSize(R.styleable.RiskLevelBar_leftAndRightMargin,10);//左右间距
        mLevelBarHeight = a.getDimensionPixelSize(R.styleable.RiskLevelBar_barHeight,30);//bar高度
        a.recycle();
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //默认测量模式为EXACTLY，否则请使用上面的方法并指定默认的宽度和高度
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
         //设置滑块在父布局的位置
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRiskLevelBar(canvas);//画条形bar
    }
    /**
     * 画风险等级条
     * @param canvas
     */
    private void drawRiskLevelBar(Canvas canvas) {

        //渐变色
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,new int[]{getResources().getColor(R.color.start),getResources().getColor(R.color.end)}, null, LinearGradient.TileMode.CLAMP);
        mPaint.setShader(linearGradient);

        //矩形左上角和右下角的点的坐标
        RectF rectF = new RectF(leftAndRightMargin,mViewHeight/2-mLevelBarHeight/2,mViewWidth-leftAndRightMargin,mViewHeight/2+mLevelBarHeight/2);
        canvas.drawRoundRect(rectF, 4, 4, mPaint);
    }
}
