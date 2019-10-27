package com.daomanager;

import com.greendao.db.DaoMaster;
import com.greendao.db.DaoSession;
import com.greendaodemo2.MyAppLication;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by qlshi on 2018/6/28.
 */
/*
要对数据表进行增删改查，需要获取该表对应的Dao类，而获取Dao类需要DaoSession。
创建一个DaoManager用于初始化数据库以及提供DaoSession。
* */

public class DaoManager {
    private static DaoManager mDaoManager;
    private DaoSession mDaoSession;
    private String DB_NAME = "test.db";
    private DaoMaster.DevOpenHelper mSQLiteOpenHelper;
    private DaoMaster mDaoMaster;

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDataBase();
        }
        return mDaoSession;
    }

    public static DaoManager getInstance() {
        if (mDaoManager == null) {
            synchronized (DaoManager.class) {
                if (mDaoManager == null) {
                    mDaoManager = new DaoManager();
                }
            }
        }
        return mDaoManager;
    }

    //初始化数据库及相关类
    private void initDataBase() {
        setDebugMode(true);//默认开启Log打印
        mSQLiteOpenHelper = new MyOpenHelper(MyAppLication.getInstance(), DB_NAME, null);//建库
        mDaoMaster = new DaoMaster(mSQLiteOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        mDaoSession.clear();//清空所有数据表的缓存
    }

    //是否开启Log
    public void setDebugMode(boolean flag) {
        MigrationHelper.DEBUG = true;//如果查看数据库更新的Log，请设置为true
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }
}
