package com.zhangying.oem1688.db;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class StuDBHelper extends SQLiteOpenHelper {
    //必须要有构造函数
    public StuDBHelper( Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
 
    //当第一次创建数据库的时候，调用该方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =  "create table stu_table(id integer  PRIMARY KEY AUTOINCREMENT ,sname varchar(20),sage int,ssex varchar(10))";
        //输出创建数据库的日志信息
        Log.i("this", "create Database------------->");
        //execSQL函数用于执行SQL语句
        db.execSQL(sql);
 
    }
 
    //当更新数据库的时候执行该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息
        Log.i("this", "update Database------------->");
    }
}