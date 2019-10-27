package com.daomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.greendao.db.DaoMaster;
import com.greendao.db.MovieCollectDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qlshi on 2018/6/28.
 */

public class MyOpenHelper extends DaoMaster.DevOpenHelper{
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, MovieCollectDao.class);
//        MigrationHelper.migrate(db,MovieCollectDao.class, UserDao.class);
    }
}
