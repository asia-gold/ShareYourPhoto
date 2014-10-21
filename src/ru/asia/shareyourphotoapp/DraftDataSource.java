package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.model.Draft;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Work with drafts from database. Add, update, delete and get 
 * drafts.
 * 
 * @author Asia
 *
 */
public class DraftDataSource {

	private SQLiteDatabase database;
	private DraftDBHelper dbHelper;
	private String[] allColumns = { DraftDBHelper.COLUMN_ID,
			DraftDBHelper.COLUMN_PHOTO, DraftDBHelper.COLUMN_PHOTO_PATH, 
			DraftDBHelper.COLUMN_EMAIL, DraftDBHelper.COLUMN_SUBJECT, 
			DraftDBHelper.COLUMN_BODY };

	public DraftDataSource(Context context) {
		dbHelper = new DraftDBHelper(context);
	}

	/**
	 * Initialize database.
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Close open database.
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * Add draft to database.
	 * 
	 * Draft data for adding.
	 * @param photo
	 * @param photoPath
	 * @param email
	 * @param subject
	 * @param body
	 * @return id of new data row.
	 */
	public long addContact(byte[] photo, String photoPath, String email, String subject,
			String body) {
		ContentValues values = new ContentValues();
		values.put(DraftDBHelper.COLUMN_PHOTO, photo);
		values.put(DraftDBHelper.COLUMN_PHOTO_PATH, photoPath);
		values.put(DraftDBHelper.COLUMN_EMAIL, email);
		values.put(DraftDBHelper.COLUMN_SUBJECT, subject);
		values.put(DraftDBHelper.COLUMN_BODY, body);
		long insertID = database.insert(DraftDBHelper.TABLE_NAME, null,
				values);
		Log.e("---------------", "id create = " + insertID);
		return insertID;
	}
	
	/**
	 * Update draft specified by id argument.
	 * 
	 * @param id	- id of draft
	 * 
	 * Draft data for updating.
	 * @param photo
	 * @param photoPath
	 * @param email
	 * @param subject
	 * @param body
	 */
	public void updateContact(long id, byte[] photo, String photoPath, String email, String subject, String body) {
		ContentValues values = new ContentValues();
		values.put(DraftDBHelper.COLUMN_PHOTO, photo);
		values.put(DraftDBHelper.COLUMN_PHOTO_PATH, photoPath);
		values.put(DraftDBHelper.COLUMN_EMAIL, email);
		values.put(DraftDBHelper.COLUMN_SUBJECT, subject);
		values.put(DraftDBHelper.COLUMN_BODY, body);
		String whereClause = null;
		if (id != 0) {
			 whereClause = DraftDBHelper.COLUMN_ID + " = " + id;
		}
		database.update(DraftDBHelper.TABLE_NAME, values, whereClause, null);
	}
	
	/**
	 * Get draft from database using id argument.
	 * 
	 * @param id	- id of draft.
	 * @return draft.
	 */
	public Draft getDraft(long id) {
		Draft draft = null;
		Cursor cursor = database.query(DraftDBHelper.TABLE_NAME, allColumns,
				DraftDBHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();

		draft = cursorToDraft(cursor);
		cursor.close();
		return draft;
	}
	
	/**
	 * Get all drafts from database.
	 * 
	 * @return ArrayList of drafts.
	 */
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
	
	/**
	 * Delete draft specified by draft argument.
	 * 
	 * @param draft - draft for deleting.
	 */
	public void deleteDraft(Draft draft) {
		long id = draft.getId();
		Log.e("----------------", "deleted with id: " + id);
		database.delete(DraftDBHelper.TABLE_NAME, DraftDBHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	/**
	 * Delete all drafts.
	 */
	public void deleteAllDrafts() {
		Log.e("----------------", "delete all");
		database.delete(DraftDBHelper.TABLE_NAME, null, null);
	}
	
	/**
	 * Get data from cursor and set data to draft.
	 * 
	 * @param cursor
	 * @return draft with data from cursor.
	 */
	private Draft cursorToDraft(Cursor cursor) {
		Draft draft = new Draft();
		draft.setId(cursor.getLong(0));
		draft.setPhoto(cursor.getBlob(1));
		draft.setPhotoPath(cursor.getString(2));
		draft.setEmail(cursor.getString(3));
		draft.setSubject(cursor.getString(4));
		draft.setBody(cursor.getString(5));
		return draft;
	}
}
