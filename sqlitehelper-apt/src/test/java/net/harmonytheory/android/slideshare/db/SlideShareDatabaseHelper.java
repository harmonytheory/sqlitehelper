package net.harmonytheory.android.slideshare.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class SlideShareDatabaseHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "slideshare.db";

	public SlideShareDatabaseHelper() {
		super(null, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
