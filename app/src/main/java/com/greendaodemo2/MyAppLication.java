package com.greendaodemo2;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.daomanager.DaoManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by qlshi on 2018/6/28.
 */

public class MyAppLication extends Application {
    public static Context context;
    private static List<Activity> lists = new ArrayList<>();
    private RefWatcher refWatcher;

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        setDataBase(this);
        ZXingLibrary.initDisplayOpinion(this);
        initRxGallery();
        initLeakCanary();
        //Fresco网页调试接口
        Fresco.initialize(this);
        //侧滑删除初始化
        initSwipeBack();
        //换肤初始化
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    private void initSwipeBack() {
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this,null);
    }

    //内存泄露检测
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //上面只能捕抓activity,下方定义方法可调用进行进一步监控
        //refWatcher = setupLeakCanary();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    //建议在此初始化RxGallery图片选择框架
    private void initRxGallery() {

    }

    private void setDataBase(Context context) {
        DaoManager.getInstance().getDaoSession();
        //DaoMaster为后面创建表实体后自动生成的类。
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "test.db", null);
    }

    public static Context getInstance() {
        return context;
    }

    //其他地方可以采用此方式进行检测内存是否泄露
    public static RefWatcher getRefWatcher(Context context) {
        MyAppLication leakApplication = (MyAppLication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    //添加activity
    public static void addActivity(Activity activity) {
        lists.add(activity);
    }

    //关闭所有activity
    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }

            lists.clear();
        }
    }
    public static void clearActivity(Activity activity) {
        if(lists.size()>0&&lists!=null) {
            lists.remove(activity);
        }
    }
}
