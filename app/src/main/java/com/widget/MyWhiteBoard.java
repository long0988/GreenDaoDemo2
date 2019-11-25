package com.widget;

import android.content.Context;
import android.graphics.Canvas;
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

    public MyWhiteBoard(Context context) {
        super(context);
        initPaint();
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        if(drawPathList!=null&&drawPathList.size()>1){
            drawPathList.remove(drawPathList.size()-1);
            invalidate();
        }
    }

    class DrawPath {
        Path path;
        Paint paint;
    }
}
