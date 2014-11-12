package ru.asia.shareyourphotoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manage contacts database creation and version changing.
 * 
 * @author Asia
 *
 */
public class DraftDBHelper extends SQLiteOpenHelper{
	
	/**
	 * Database name and version.
	 */
	private static final String DATABASE_NAME = "shareyourphoto.db";
	private static final int DATADASE_VERSION = 1;
	
	/**
	 * Table name.
	 */
	public static final String TABLE_NAME = "drafts";
	
	/**
	 * Columns names.
	 */
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PHOTO = "photo";
	public static final String COLUMN_PHOTO_PATH = "path";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_SUBJECT = "subject";
	public static final String COLUMN_BODY = "body";
	
	/**
	 * Database creation SQL statement.
	 */
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PHOTO + " BLOB, " + COLUMN_PHOTO_PATH + " text, "
			+ COLUMN_EMAIL + " text not null, " + COLUMN_SUBJECT + " text, " 
			+ COLUMN_BODY + " text" + ");";
	
	public DraftDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATADASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXESTS " + TABLE_NAME);
		onCreate(db);		
	}

}
