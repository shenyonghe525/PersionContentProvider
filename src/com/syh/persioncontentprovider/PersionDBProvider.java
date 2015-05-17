package com.syh.persioncontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersionDBProvider extends ContentProvider {
	// 定义一个uri的匹配器，用来匹配uri，如果不匹配返回-1
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final String authority = "com.syh.persioncontentprovider.persionprovider";
	private static final int INSERT = 1;
	private static final int DELETE = 2;
	private static final int UPDATE = 3;
	private static final int QUERY = 4;
	private static final int QUERYONE = 5;
	private PersionSqliteDB helper;
	// content://com.syh.persioncontentprovider.persionprovider/insert 添加
	// content://com.syh.persioncontentprovider.persionprovider/delete 删除
	// content://com.syh.persioncontentprovider.persionprovider/update 更新
	// content://com.syh.persioncontentprovider.persionprovider/query 查询
	static {
		// 添加一组匹配规则
		matcher.addURI(authority, "insert", INSERT);
		matcher.addURI(authority, "delete", DELETE);
		matcher.addURI(authority, "update", UPDATE);
		matcher.addURI(authority, "query", QUERY);
		matcher.addURI(authority, "query/#", QUERYONE);
	}
    /**
     * 当内容提供者被创建时调用，初始化
     */
	@Override
	public boolean onCreate() {
		helper = new PersionSqliteDB(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (matcher.match(uri) == QUERY) {
			// 返回查询的结果集
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor cursor = db.query("persion", projection, selection,
					selectionArgs, null, null, sortOrder);
			return cursor;
		} else if (matcher.match(uri) == QUERYONE) {
			// 返回查询的结果集
			SQLiteDatabase db = helper.getReadableDatabase();
			long id = ContentUris.parseId(uri);
			Cursor cursor = db.query("persion", projection, "id = ?",
					new String[]{id+""}, null, null, sortOrder);
			return cursor;
		} else {
			throw new IllegalArgumentException("uri路径不匹配,不能执行查询操作！");
		}
	}

	@Override
	public String getType(Uri uri) {
		if (matcher.match(uri) == QUERY) {
			return "vnd.android.cursor.dir/persion";
		} else if (matcher.match(uri) == QUERYONE) {
			return "vnd.android.cursor.item/persion";
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (matcher.match(uri) == INSERT) {
			SQLiteDatabase db = helper.getReadableDatabase();
			db.insert("persion", null, values);
			db.close();
		} else {
			throw new IllegalArgumentException("uri路径不匹配,不能执行插入操作！");
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (matcher.match(uri) == DELETE) {
			SQLiteDatabase db = helper.getReadableDatabase();
			db.delete("persion", selection, selectionArgs);
			db.close();
		} else {
			throw new IllegalArgumentException("uri路径不匹配,不能执行删除操作！");
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		if (matcher.match(uri) == UPDATE) {
			SQLiteDatabase db = helper.getReadableDatabase();
			db.update("persion", values, selection, selectionArgs);
			db.close();
		} else {
			throw new IllegalArgumentException("uri路径不匹配,不能执行更新操作！");
		}
		return 0;
	}

}
