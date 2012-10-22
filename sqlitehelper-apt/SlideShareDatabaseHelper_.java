package net.harmonytheory.android.slideshare.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class SlideShareDatabaseHelper_<T> extends net.harmonytheory.android.slideshare.db.SlideShareDatabaseHelper {
	@Override
	public void onCreate(SQLiteDatabase db) {

		net.harmonytheory.android.slideshare.data.OembedSqliteHelper.execCreateSql(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onUpgrade(db, oldVersion, newVersion);
	}

	public abstract String getTable();

	public abstract String[] getColumns();

	public abstract T cursorToBean(Cursor c);

	public abstract ContentValues beanToContentValues(T bean);

	public List<T> selectAll(String order) {
		Cursor c = query(getTable(), getColumns(), null, null, null, null, order, null);
		List<T> list = new ArrayList<T>();
		if (c.moveToFirst()) {
			while (c.moveToNext()) {
				list.add(cursorToBean(c));
			}
		}
		return list;
	}

	public long insert(T bean) {
        return insert(Arrays.asList(bean));
	}
	public long insert(List<T> beanList) {
        SQLiteDatabase db = getWritableDatabase();
        long insert = 0;
        db.beginTransaction();
        try {
        	for (T bean : beanList) {
        		insert = db.insert(getTable(), null, beanToContentValues(bean));
        	}
        	db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
        return insert;
	}
	public long insertOrThrow(T bean) {
        return insertWithOnConflict(bean, SQLiteDatabase.CONFLICT_NONE);
	}
	public long insertOrThrow(List<T> beanList) {
        return insertWithOnConflict(beanList, SQLiteDatabase.CONFLICT_NONE);
	}
	public long insertWithOnConflict(T bean, int conflictAlgorithm) {
        return insertWithOnConflict(Arrays.asList(bean), conflictAlgorithm);
	}
	public long insertWithOnConflict(List<T> beanList, int conflictAlgorithm) {
        SQLiteDatabase db = getWritableDatabase();
        long insert = 0;
        db.beginTransaction();
        try {
        	for (T bean : beanList) {
        		insert = db.insertWithOnConflict(getTable(), null, beanToContentValues(bean), conflictAlgorithm);
        	}
        	db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
        return insert;
	}
	
	public Cursor query(String table, String[] columns, String selection, 
				String[] selectionArgs, String groupBy, String having,
				String orderBy, String limit) {
		return getWritableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
}
