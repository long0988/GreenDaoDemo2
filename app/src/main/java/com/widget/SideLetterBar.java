package com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by qlshi on 2018/7/17.
 */

public class SideLetterBar extends View {
    // 字母
    public static String[] mWords = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    // 选中标志
    private int choose = -1;
    //画笔
    private Paint paint = new Paint();
    /*字母背景画笔*/
    private Paint bgPaint = new Paint();
    //中间提示的view
    private TextView mTextDialog;
    //对应总高度
    private int height;
    //对应总宽度
    private int width;
    //默认画笔颜色
    private String mDefaultColor = null;
    //选中的画笔颜色
    private String mChooseColor = null;
    private float mSize = 0;
    private OnLettersChangeListener mLettersChangeListener = null;
    private boolean bgColor;

    public SideLetterBar(Context context) {
        super(context);
    }

    public SideLetterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setPaintColor(String defaultColor) {
        this.mDefaultColor = defaultColor;
    }

    public void setPaintChooseColor(String chooseColor) {
        this.mChooseColor = chooseColor;
    }

    public void setTextSize(float size) {
        this.mSize = size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bgColor){
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        height = getHeight();//对应高度
        width = getWidth(); //对应宽度
        int length = mWords.length;
        //每个一个字母高度
        int singleHeight = height / length;
        for (int i = 0; i < length; i++) {
            if (mDefaultColor != null) {
                paint.setColor(Color.parseColor(mDefaultColor));
            } else {
//                paint.setColor(Color.rgb(33, 65, 98));
                paint.setColor(Color.GRAY);
            }
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            if (mSize > 0) {
                paint.setTextSize(mSize);
            } else {
                paint.setTextSize(30);
            }
            if (choose == i) {
                //绘制文字圆形背景
//                bgPaint.setColor(Color.WHITE);
                bgPaint.setColor(Color.parseColor("#3399ff"));
                canvas.drawCircle(width / 2, singleHeight - 10 + i * singleHeight, 23, bgPaint);
                paint.setColor(Color.WHITE);
                if (mChooseColor != null) {
                    paint.setColor(Color.parseColor(mChooseColor));
                }
                paint.setFakeBoldText(true);
            }
            //x坐标等于宽度一半-字符宽度的一半
            float xPos = width / 2 - paint.measureText(mWords[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mWords[i], xPos, yPos, paint);
//            paint.reset();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                bgColor=true;
                float y = event.getY();
                int index = (int) (y / height * mWords.length);
                if (index != choose) {
                    if (index >= 0 && index < mWords.length) {
                        choose = index;
                        invalidate();
                        String letter = mWords[choose];
                        if (mLettersChangeListener != null) {
                            mLettersChangeListener.LettersChange(letter);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(letter);
                            mTextDialog.setVisibility(VISIBLE);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                bgColor=false;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    /*设置当前按下的是那个字母*/
    public void setTouchIndex(String letter) {
        for (int i = 0; i < mWords.length; i++) {
            if (mWords[i].equals(letter)) {
                choose = i;
                invalidate();
                return;
            }
        }
    }

    public void setOnLettersChangeListener(OnLettersChangeListener listener) {
        this.mLettersChangeListener = listener;
    }

    public interface OnLettersChangeListener {
        void LettersChange(String letter);
    }
}
