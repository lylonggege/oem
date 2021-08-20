package com.zhangying.oem1688.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SearchRecodeOpenHelper extends SQLiteOpenHelper {
    private static String name = "record.db";
    private static Integer version = 1;
    private String creatSql = "create table records(id integer primary key autoincrement,name varchar(200))";

    public SearchRecodeOpenHelper(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creatSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
