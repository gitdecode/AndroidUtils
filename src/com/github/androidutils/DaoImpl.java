package com.github.androidutils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Constants;

public class DaoImpl implements Dao {
	private DBOpenHelper openHelper;

	public DaoImpl(Context context) {
		String dbName = DBOpenHelper.DBNAME;
		openHelper = new DBOpenHelper(context, dbName);
	}

	final static class PojoUtil {

		public static void loadBean(Object bean, Cursor cursor)
				throws Exception {
			Field[] fields = bean.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Class<?> type = field.getType();
				int index = cursor.getColumnIndex(field.getName());
				if (index > -1) {
					if ("java.lang.String".equals(type.getName())) {
						field.set(bean, cursor.getString(index));
					} else if ("int".equals(type.getName())) {
						field.setInt(bean, cursor.getInt(index));
					}
				}
			}
		}
	}

	@Override
	public List<Object> getCountryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getStateList(String countryNameEn, String countryCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getCityList(String countryNameEn, String stateNameEn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdateCache(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetRecentCache(Object[] object) {
		// TODO Auto-generated method stub

	}
}
