package com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyWhiteBoard extends View {
    private Paint paint;
    private Path path;
    private float downX, downY;
    private float tempX, tempY;
    private List<DrawPath> drawPathList = new ArrayList<>();
    private List<DrawPath> savePathList=new ArrayList<>();
    private float paintWidth=10;
    private int mPaontColor=Color.BLACK;

    public MyWhiteBoard(Context context) {
        this(context,null);
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(mPaontColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if(drawPathList!=null&&!drawPathList.isEmpty()){
            for(DrawPath drawPath:drawPathList){
                if(drawPath!=null){
                    canvas.drawPath(drawPath.path, drawPath.paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                path = new Path();//每次手指下去都是一条新的路径
                path.moveTo(downX, downY);
                //添加进路径数组
                DrawPath drawPath = new DrawPath();
                drawPath.paint = paint;
                drawPath.path = path;
                drawPathList.add(drawPath);
                invalidate();
                tempX = downX;
                tempY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                path.quadTo(tempX, tempY, moveX, moveY);
                invalidate();
                tempX = moveX;
                tempY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                initPaint();//每次手指抬起都要重置下画笔,不然画笔会保存了之前的设置什么画笔的属性会引起bug
                break;
        }
        return true;
    }
    public void undo(){
        if(drawPathList!=null&&drawPathList.size()>0){
            savePathList.add(drawPathList.get(drawPathList.size()-1));
            drawPathList.remove(drawPathList.size()-1);
            invalidate();
        }
    }
    /**
     * 反撤销功能
     */
    public void reundo() {
        if(savePathList!=null&&!savePathList.isEmpty()){
            drawPathList.add(savePathList.get(savePathList.size()-1));
            savePathList.remove(savePathList.size()-1);
            invalidate();
        }
    }
    /**
     * 改变画笔颜色
     * @param color
     */
    public void resetPaintColor(int color) {
        mPaontColor=color;
        paint.setColor(color);
    }
    /**
     * 放大就是改变画笔的宽度
     */
    public void scalePaintWidth() {
        paintWidth+=2;
        paint.setStrokeWidth(paintWidth);
    }
    /**
     * 缩小就是改变画笔的宽度
     */
    public void reducePaintWidth() {
        if(paintWidth>2){
            paintWidth-=2;
        }
        paint.setStrokeWidth(paintWidth);
    }

    /**
     * 设置画笔宽度
     */
    public void setPaintWidth(float paintWidth){
        this.paintWidth=paintWidth;
        paint.setStrokeWidth(paintWidth);
    }
    /**
     * 橡皮擦功能 把画笔的颜色和view的背景颜色一样就ok,然后把画笔的宽度变大了,擦除的时候显得擦除范围大点
     */
    public void eraser(int bgcolor) {
        mPaontColor=bgcolor;
        paint.setColor(bgcolor);//这是view背景的颜色
        paintWidth=paintWidth+10;
        paint.setStrokeWidth(paintWidth);
    }

    class DrawPath {
        Path path;
        Paint paint;
    }
}
