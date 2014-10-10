package ru.asia.shareyourphotoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DraftDBHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "shareyourphoto.db";
	private static final int DATADASE_VERSION = 1;
	
	public static final String TABLE_NAME = "drafts";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PHOTO = "photo";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_SUBJECT = "subject";
	public static final String COLUMN_BODY = "body";
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PHOTO + " BLOB, " + COLUMN_EMAIL + " text not null, " 
			+ COLUMN_SUBJECT + " text, " + COLUMN_BODY + " text" + ");";
	
	public DraftDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATADASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		Log.e("-------------------", "DB created");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXESTS " + TABLE_NAME);
		onCreate(db);		
	}

}
