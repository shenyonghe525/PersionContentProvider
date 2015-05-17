package com.syh.persioncontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersionSqliteDB extends SQLiteOpenHelper {
	private static final String TABLE_NAME = "persion";
	private static final String TAG = "SQLiteOpenHelper";
	
	/**
	 * 数据库的构造方法 用来定义数据库的名称 数据库查询的结果集 以及数据库的版本
	 * 
	 * @param context
	 * @param name 数据库名称
	 * @param version 版本号（>=1）
	 */
	public PersionSqliteDB(Context context) {
		super(context, "persion.db", null, 3);

	}

	/**
	 * 数据库第一次被创建时调用
	 * 
	 * @param db 被创建的数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "+TABLE_NAME+" (id integer primary key autoincrement,name varchar(20),number varchar(20))");
	}
    /**
     * 当数据库的版本号发生变化（增加的时候）时被调用。
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        Log.i(TAG, "数据库版本发生变化了");
        db.execSQL("alter table persion add acount varchar(20)");
	}

}
