package com.github.androidutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SqliteUtils {

	private static volatile SqliteUtils instance;

	private DBOpenHelper dbHelper;
	private SQLiteDatabase db;

	private SqliteUtils(Context context,String dbName) {
		dbHelper = new DBOpenHelper(context,dbName);
		db = dbHelper.getWritableDatabase();
	}

	public static SqliteUtils getInstance(Context context, String dbName) {
		if (instance == null) {
			synchronized (SqliteUtils.class) {
				if (instance == null) {
					instance = new SqliteUtils(context,dbName);
				}
			}
		}
		return instance;
	}

	public SQLiteDatabase getDb() {
		return db;
	}
}