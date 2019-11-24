package com.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
    private boolean mScrollble = false;
    //    private boolean isCanScroll = true;
    private boolean noScroll = false;


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    /**
//     * false 不能换页， true 可以滑动换页
//     * @param isCanScroll
//     */
//    public void setScanScroll(boolean isCanScroll) {
//        this.isCanScroll = isCanScroll;
//    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return isCanScroll&&super.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return isCanScroll&&super.onTouchEvent(ev);
//    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !noScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !noScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        //false 去除滚动效果
        super.setCurrentItem(item, false);
    }
//    public MyViewPager(Context context) {
//        super(context);
//    }
//
//    public MyViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (!mScrollble) {
//            return false;
//        }
//        return super.onTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (!mScrollble) {
//            return false;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    public boolean isCanScrollble() {
//        return mScrollble;
//    }
//
//    public void setCanScrollble(boolean scrollble) {
//        this.mScrollble = scrollble;
//    }
}
