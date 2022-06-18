package com.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private int canvasBG=Color.WHITE;
    private int[] mColorArr;
    private int[] mLighterColorArr;
    private int curIndex = 0;
    private boolean ifLighter=false;
    private Paint bitmapPaint;
    private Bitmap bitmap;
    private Canvas canvas;
    private int mWidth;
    private int mHeight;

    public MyWhiteBoard(Context context) {
        this(context,null);
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        initPaint();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mHeight = MeasureSpec.getSize(heightMeasureSpec);
//        setMeasuredDimension(mWidth, mHeight);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        if(bitmap==null){
//            bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
//        }
//        canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.TRANSPARENT);
//    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(paintWidth);
        paint.setColor(mPaontColor);
        if(ifLighter){
            paint.setAlpha(80);
        }
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawColor(canvasBG);
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
    /**
     * 画布背景色
     * @param canvasBg
     */
    public  void setCanvasBg(int canvasBg){
        this.canvasBG=canvasBg;
    }

    /**
     * 选择颜色集合
     * @param colorArr
     */
    public void setColorArr(int [] colorArr){
        mPaontColor=colorArr[0];
        mColorArr=colorArr;
        initPaint();
    }
    /**
     * 荧光笔的颜色集合,暂时保留
     */
    public void setLighterColorArr(int [] colorArr){
        mLighterColorArr=colorArr;
    }
    /**
     * 撤销
     */
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

    /**
     * 钢笔
     */
    public void setPen(){
        paintWidth=10;
        ifLighter=false;
        mPaontColor=mColorArr[curIndex];
        paint.setColor(mPaontColor);
        paint.setStrokeWidth(paintWidth);
    }
    /**
     * 荧光笔
     */
    public void setHighLighter(){
        paintWidth=25;
//        mPaontColor=mLighterColorArr[curIndex];
//        paint.setColor(mPaontColor);
        ifLighter=true;
        setPaintAlpha();
        paint.setStrokeWidth(paintWidth);
    }
    public void setPaintAlpha(){
        paint.setAlpha(80);
    }
    /**
     * 颜色更换
     */
    public void setCurrColor(int index){
        this.curIndex=index;
        mPaontColor=mColorArr[index];
        paint.setColor(mPaontColor);
        if(ifLighter){
            setPaintAlpha();
        }
    }
    public Bitmap getBitmap() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        doDraw(canvas);
        return bitmap;
    }
    /**
     * 开始进行绘画
     *
     * @param canvas
     */
    private void doDraw(Canvas canvas) {
        canvas.drawColor(canvasBG);
        if(drawPathList!=null&&!drawPathList.isEmpty()){
            for(DrawPath drawPath:drawPathList){
                if(drawPath!=null){
                    canvas.drawPath(drawPath.path, drawPath.paint);
                }
            }
        }
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

    public void loadImage(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        canvas.setBitmap(this.bitmap);
        bitmap.recycle();
        invalidate();
    }
    public boolean saveImage(String filePath, String filename, Bitmap.CompressFormat format,
                             int quality) {
        if (quality > 100) {
            Toast.makeText(getContext(),"质量不能高于100",Toast.LENGTH_SHORT).show();
            return false;
        }
        File file;
        filename=filename+System.currentTimeMillis();
        FileOutputStream out = null;
        try {
            switch (format) {
                case PNG:
                    file = new File(filePath, filename + ".png");
                    out = new FileOutputStream(file);
                    return getBitmap().compress(Bitmap.CompressFormat.PNG, quality, out);
                case JPEG:
                    file = new File(filePath, filename + ".jpg");
                    out = new FileOutputStream(file);
                    return getBitmap().compress(Bitmap.CompressFormat.JPEG, quality, out);
                default:
                    file = new File(filePath, filename + ".png");
                    out = new FileOutputStream(file);
                    return getBitmap().compress(Bitmap.CompressFormat.PNG, quality, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    class DrawPath {
        Path path;
        Paint paint;
    }
}
