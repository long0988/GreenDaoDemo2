package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by qlshi on 2016/9/3.
 */

public class DipPxUtils {
    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(int pxValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(int dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, displayMetrics);
    }

    /**
     * 是否为竖屏
     */
    public static boolean isPortrait(Context context) {
        Point screenResolution = getScreenResolution(context);
        return screenResolution.y > screenResolution.x;
    }

    /**
     * 屏幕分辨率
     *
     * @param context
     * @return
     */
    static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        display.getSize(screenResolution);
        return screenResolution;
    }

    /**
     * 获取屏幕区域
     */
    public static Rect getScreenRect() {
        //context.getResources().getDisplayMetrics()
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        Rect rect = new Rect(0, 0, dm.widthPixels, dm.heightPixels);
        return rect;
    }

    /**
     * 获取屏幕区域2
     */
    public static DisplayMetrics getWindowScreen(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        //依赖activity
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取屏幕区域
     */
    public static DisplayMetrics getScreen() {
        //context.getResources().getDisplayMetrics()
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm;
    }

    /**
     * 屏幕宽度
     */
    public static int screenWidth() {
        return getScreen().widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @return
     */
    public static int screenHeight() {
        return getScreen().heightPixels;
    }

    /**
     * 屏幕密度
     *
     * @return
     */
    public static float screenDensity() {
        return getScreen().density;
    }

    /**
     * 屏幕像素密度
     *
     * @return
     */
    public static float screenDensityDpi() {
        return getScreen().densityDpi;
    }

    /**
     * 获取状态栏高度常见方式
     * 1、依赖activity
     */
    public static int getStatusH(Activity ctx) {
        Rect s = new Rect();
        ctx.getWindow().getDecorView().getWindowVisibleDisplayFrame(s);
        return s.top;
    }

    /**
     * 2、反射获取
     */
    public static int getStatusH(Context ctx) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = ctx.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 3、获取状态栏高度(常用)
     */
    public static int getStatusHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? activity.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    //录音计时格式化
    public static String formatSeconds(int seconds) {
        return getTwoDecimalsValue(seconds / 3600) + ":"
                + getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }
}
