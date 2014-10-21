//package ru.asia.shareyourphotoapp;
//
//import android.content.ContentProvider;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.util.Log;
//
//public class DraftsProvider extends ContentProvider {
//
//	private DraftDBHelper dbHelper;
//	private SQLiteDatabase database;
//
//	private static final String AUTHORITY = "ru.asia.shareyourphotoapp";
//	private static final String DRAFTS_PATH = "drafts";
//	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
//			+ "/" + DRAFTS_PATH);
//
//	// Типы данных
//	// набор строк
//	public static final String DRAFT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
//			+ AUTHORITY + "." + DRAFTS_PATH;
//
//	// одна строка
//	public static final String DRAFT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
//			+ AUTHORITY + "." + DRAFTS_PATH;
//
//	// //UriMatcher
//	// общий Uri
//	private static final int URI_DRAFTS = 1;
//
//	// Uri с указанным ID
//	private static final int URI_DRAFTS_ID = 2;
//
//	// описание и создание UriMatcher
//	private static final UriMatcher uriMatcher;
//	static {
//		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//		uriMatcher.addURI(AUTHORITY, DRAFTS_PATH, URI_DRAFTS);
//		uriMatcher.addURI(AUTHORITY, DRAFTS_PATH + "/#", URI_DRAFTS_ID);
//	}
//
//	@Override
//	public boolean onCreate() {
//		dbHelper = new DraftDBHelper(getContext());
//		return false;
//	}
//
//	@Override
//	public Cursor query(Uri uri, String[] projection, String selection,
//			String[] selectionArgs, String sortOrder) {
//		Log.d("----------", "query, " + uri.toString());
//		// проверяем Uri
//		switch (uriMatcher.match(uri)) {
//		case URI_DRAFTS: // общий Uri
//			Log.d("----------", "URI_DRAFTS");
//			break;
//		case URI_DRAFTS_ID: // Uri с ID
//			String id = uri.getLastPathSegment();
//			Log.d("----------", "URI_DRAFTS_ID, " + id);
//			// добавляем ID к условию выборки
//			if (TextUtils.isEmpty(selection)) {
//				selection = dbHelper.COLUMN_ID + " = " + id;
//			} else {
//				selection = selection + " AND " + dbHelper.COLUMN_ID + " = "
//						+ id;
//			}
//			break;
//		default:
//			throw new IllegalArgumentException("Wrong URI: " + uri);
//		}
//		database = dbHelper.getWritableDatabase();
//		Log.d("----------", "selection, " + selection);
//		Cursor cursor = database.query(dbHelper.TABLE_NAME, projection,
//				selection, selectionArgs, null, null, sortOrder);
//		// просим ContentResolver уведомлять этот курсор
//		// об изменениях данных в CONTACT_CONTENT_URI
//		cursor.setNotificationUri(getContext().getContentResolver(),
//				CONTENT_URI);
//		cursor.moveToFirst();
//		return cursor;
//	}
//
//	@Override
//	public Uri insert(Uri uri, ContentValues values) {
//		Log.d("----------", "insert, " + uri.toString());
//		if (uriMatcher.match(uri) != URI_DRAFTS) {
//			throw new IllegalArgumentException("Wrong URI: " + uri);
//		}
//		database = dbHelper.getWritableDatabase();
//		long rowID = database.insert(dbHelper.TABLE_NAME, null, values);
//		Uri resultUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
//		// уведомляем ContentResolver, что данные по адресу resultUri изменились
//		getContext().getContentResolver().notifyChange(resultUri, null);
//		return resultUri;
//	}
//
//	@Override
//	public int delete(Uri uri, String selection, String[] selectionArgs) {
//		Log.d("----------", "delete, " + uri.toString());
//		switch (uriMatcher.match(uri)) {
//		case URI_DRAFTS:
//			Log.d("----------", "URI_DRAFTS");
//			break;
//		case URI_DRAFTS_ID:
//			String id = uri.getLastPathSegment();
//			Log.d("----------", "URI_DRAFTS_ID, " + id);
//			if (TextUtils.isEmpty(selection)) {
//				selection = dbHelper.COLUMN_ID + " = " + id;
//			} else {
//				selection = selection + " AND " + dbHelper.COLUMN_ID + " = "
//						+ id;
//			}
//			break;
//		default:
//			throw new IllegalArgumentException("Wrong URI: " + uri);
//		}
//		database = dbHelper.getWritableDatabase();
//		int cnt = database
//				.delete(dbHelper.TABLE_NAME, selection, selectionArgs);
//		getContext().getContentResolver().notifyChange(uri, null);
//		return cnt;
//	}
//
//	@Override
//	public int update(Uri uri, ContentValues values, String selection,
//			String[] selectionArgs) {
//		Log.d("----------", "update, " + uri.toString());
//		switch (uriMatcher.match(uri)) {
//		case URI_DRAFTS:
//			Log.d("----------", "URI_DRAFTS");
//			break;
//		case URI_DRAFTS_ID:
//			String id = uri.getLastPathSegment();
//			Log.d("----------", "URI_DRAFTS_ID, " + id);
//			if (TextUtils.isEmpty(selection)) {
//				selection = dbHelper.COLUMN_ID + " = " + id;
//			} else {
//				selection = selection + " AND " + dbHelper.COLUMN_ID + " = "
//						+ id;
//			}
//			break;
//		default:
//			throw new IllegalArgumentException("Wrong URI: " + uri);
//		}
//		database = dbHelper.getWritableDatabase();
//		int cnt = database.update(dbHelper.TABLE_NAME, values, selection, selectionArgs);
//		getContext().getContentResolver().notifyChange(uri, null);
//		return cnt;
//	}
//
//	@Override
//	public String getType(Uri uri) {
//		Log.d("----------", "getType, " + uri.toString());
//	    switch (uriMatcher.match(uri)) {
//	    case URI_DRAFTS:
//	      return DRAFT_CONTENT_TYPE;
//	    case URI_DRAFTS_ID:
//	      return DRAFT_CONTENT_ITEM_TYPE;
//	    }
//	    return null;
//	}
//
//}
