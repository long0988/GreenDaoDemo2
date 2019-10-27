package com.utils;

import android.util.Log;

import com.greendaodemo2.BuildConfig;


public class LogUtils {
    public static void logD(String tag, String msg) {
        if (!BuildConfig.DEBUG) return;
        Log.d(tag, msg);
    }

    public static void logE(String tag, String msg) {
        if (!BuildConfig.DEBUG) return;
        Log.e(tag, msg);
    }

    public static void LogW(String tag, String msg) {
        if (!BuildConfig.DEBUG) return;
        Log.w(tag, msg);
    }
}
