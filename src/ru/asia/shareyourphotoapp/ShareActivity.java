package ru.asia.shareyourphotoapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShareActivity extends ActionBarActivity {

	private EditText etEmail;
	private EditText etSubject;
	private EditText etBody;
	private Button btnShare;

	private Uri draftUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

		etEmail = (EditText) findViewById(R.id.etEmail);
		etSubject = (EditText) findViewById(R.id.etSubject);
		etBody = (EditText) findViewById(R.id.etBody);
		btnShare = (Button) findViewById(R.id.btnShare);

		Bundle extras = getIntent().getExtras();

		// // check from the saved Instance
		// draftUri = (savedInstanceState == null) ? null : (Uri)
		// savedInstanceState
		// .getParcelable(DraftsProvider.DRAFT_CONTENT_ITEM_TYPE);

		if (extras != null) {
			draftUri = extras
					.getParcelable(DraftsProvider.DRAFT_CONTENT_ITEM_TYPE);
		}
		fillData(draftUri);
		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// To Default Email Client

				if (TextUtils.isEmpty(etEmail.getText().toString())) {
					Toast.makeText(ShareActivity.this,
							getResources().getString(R.string.no_email),
							Toast.LENGTH_LONG).show();
				} else {
					saveData();
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void fillData(Uri uri) {
		if (uri != null) {
			Log.e("----------", "fillData uri " + uri);
			String[] projection = { DraftDBHelper.COLUMN_EMAIL,
					DraftDBHelper.COLUMN_SUBJECT, DraftDBHelper.COLUMN_BODY };
			Cursor cursor = getContentResolver().query(uri, projection, null,
					null, null);
			Log.e("----------", "cursor fill data " + cursor.getString(cursor
					.getColumnIndex(DraftDBHelper.COLUMN_EMAIL)));
			etEmail.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(DraftDBHelper.COLUMN_EMAIL)));
			etSubject.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(DraftDBHelper.COLUMN_SUBJECT)));
			etBody.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(DraftDBHelper.COLUMN_BODY)));
			cursor.close();
		}
	}

	private void saveData() {
		String email = etEmail.getText().toString();
		String subject = etSubject.getText().toString();
		String body = etBody.getText().toString();

		if (email.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(DraftDBHelper.COLUMN_EMAIL, email);
		values.put(DraftDBHelper.COLUMN_SUBJECT, subject);
		values.put(DraftDBHelper.COLUMN_BODY, body);

		if (draftUri == null) {
			// New draft
			draftUri = getContentResolver().insert(
					DraftsProvider.CONTENT_URI, values);
		} else {
			// Update draft
			getContentResolver().update(draftUri, values, null, null);
		}
	}
}
