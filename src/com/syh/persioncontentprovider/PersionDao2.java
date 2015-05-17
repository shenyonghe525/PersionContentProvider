package com.syh.persioncontentprovider;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author shenyonghe
 * 
 *         2014-10-8
 */
public class PersionDao2 {
	private PersionSqliteDB persionSqliteDB;

	public PersionDao2(Context context) {
		this.persionSqliteDB = new PersionSqliteDB(context);
	}

	/**
	 * 向数据库中添加数据 Tile:add
	 * 
	 * @param name 名字
	 * @param number 电话号 void
	 */
	public long add(String name, String number) {
		SQLiteDatabase db = persionSqliteDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("number", number);
		long id = db.insert("persion", null, values);
		db.close();
		return id;
	}

	/**
	 * 查询记录是否存在 Tile:findByName
	 * 
	 * @param name 名字 void
	 */
	public boolean find(String name) {
		boolean result = false;
		SQLiteDatabase db = persionSqliteDB.getReadableDatabase();
		Cursor cursor = db.query("persion", null, "name = ?",
				new String[] { name }, null, null, null);
		result = cursor.moveToNext();
		cursor.close();
		db.close();
		return result;
	}

	/**
	 * 修改一条记录 Tile:update
	 * 
	 * @param name 需要更改人的姓名
	 * @param newNumber 新的电话号码 void
	 */
	public int update(String name, String newnumber) {
		SQLiteDatabase db = persionSqliteDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", newnumber);
		int number = db.update("persion", values, "name = ?",
				new String[] { name });
		db.close();
		return number;
	}

	/**
	 * 删除一条记录 Tile:delete
	 * 
	 * @param name 要删除人的姓名 void
	 */
	public int delete(String name) {
		SQLiteDatabase db = persionSqliteDB.getWritableDatabase();
		int number = db.delete("persion", "name = ?", new String[] { name });
		db.close();
		return number;
	}

	/**
	 * 查询所有数据 Tile:getAll
	 * 
	 * @return List<Persion> 以链表形式返回数据
	 */
	public List<Persion> getAll() {
		List<Persion> persionList = new ArrayList<Persion>();
		SQLiteDatabase db = persionSqliteDB.getReadableDatabase();
		Cursor cursor = db.query("persion", new String[] { "name", "id",
				"number" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			int id = cursor.getColumnIndex("id");
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String number = cursor.getString(cursor.getColumnIndex("number"));
			Persion persion = new Persion(id, name, number);
			persionList.add(persion);
		}
		cursor.close();
		db.close();
		return persionList;
	}

	/**
	 * 数据库转账事务 Tile:transferAccounts
	 * 
	 * @throws Exception void
	 */
	public void transferAccounts() throws Exception {
		SQLiteDatabase db = persionSqliteDB.getWritableDatabase();
		db.beginTransaction();
		try {
			db.execSQL("update persion set acount=acount-1000 where name= ?",
					new Object[] { "zhangsan" });
			db.execSQL("update persion set acount=acount+1000 where name= ?",
					new Object[] { "lisi" });
			// 标记数据库事务执行成功
			db.setTransactionSuccessful();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			// TODO: handle exception
			db.endTransaction();
			db.close();
		}
	}
}
