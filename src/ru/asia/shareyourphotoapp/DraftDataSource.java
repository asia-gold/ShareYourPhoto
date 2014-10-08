package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.model.Draft;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DraftDataSource {

	private SQLiteDatabase database;
	private DraftDBHelper dbHelper;
	private String[] allColumns = { DraftDBHelper.COLUMN_ID,
			DraftDBHelper.COLUMN_PHOTO, DraftDBHelper.COLUMN_EMAIL,
			DraftDBHelper.COLUMN_SUBJECT, DraftDBHelper.COLUMN_BODY };

	public DraftDataSource(Context context) {
		dbHelper = new DraftDBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Return id of new data row
	public long addContact(byte[] photo, String email, String subject,
			String body) {
		ContentValues values = new ContentValues();
		values.put(DraftDBHelper.COLUMN_PHOTO, photo);
		values.put(DraftDBHelper.COLUMN_EMAIL, email);
		values.put(DraftDBHelper.COLUMN_SUBJECT, subject);
		values.put(DraftDBHelper.COLUMN_BODY, body);
		long insertID = database.insert(DraftDBHelper.TABLE_NAME, null,
				values);
		Log.e("---------------", "id create = " + insertID);
		return insertID;
	}
	
	public Draft getDraft(long id) {
		Draft draft = null;
		Cursor cursor = database.query(DraftDBHelper.TABLE_NAME, allColumns,
				DraftDBHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();

		draft = cursorToDraft(cursor);
		cursor.close();
		return draft;
	}
	
	public ArrayList<Draft> getAllDrafts() {
		ArrayList<Draft> drafts = new ArrayList<Draft>();

		Cursor cursor = database.query(DraftDBHelper.TABLE_NAME, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Draft draft = cursorToDraft(cursor);
			drafts.add(draft);
			cursor.moveToNext();
		}
		cursor.close();
		return drafts;
	}
	
	public void deleteDraft(Draft draft) {
		long id = draft.getId();
		Log.e("----------------", "deleted with id: " + id);
		database.delete(DraftDBHelper.TABLE_NAME, DraftDBHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public void deleteAllDrafts() {
		Log.e("----------------", "delete all");
		database.delete(DraftDBHelper.TABLE_NAME, null, null);
	}
	
	private Draft cursorToDraft(Cursor cursor) {
		Draft draft = new Draft();
		draft.setId(cursor.getLong(0));
		draft.setPhoto(cursor.getBlob(1));
		draft.setEmail(cursor.getString(2));
		draft.setSubject(cursor.getString(3));
		draft.setBody(cursor.getString(4));
		return draft;
	}
}
