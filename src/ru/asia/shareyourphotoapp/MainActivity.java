package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.model.Draft;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ActionBarActivity {

	private ListView lvDrafts;
	private ArrayList<Draft> data;
	// private DraftsAdapter adapter;

	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvDrafts = (ListView) findViewById(R.id.lvDrafts);

		fillListView();
		
		lvDrafts.setAdapter(adapter);
		lvDrafts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Cursor cursor = (Cursor)lvDrafts.getItemAtPosition(position);
				int itemId = cursor.getInt(cursor
						.getColumnIndexOrThrow(DraftDBHelper.COLUMN_ID));
				Log.e("-------", "item id " + itemId );
				Intent intent = new Intent(MainActivity.this, ShareActivity.class);
			    Uri draftUri = Uri.parse(DraftsProvider.CONTENT_URI + "/" + itemId);
			    intent.putExtra(DraftsProvider.DRAFT_CONTENT_ITEM_TYPE, draftUri);
			    startActivity(intent);
			}
		});

//		data = new ArrayList<>();
//		Draft d1 = new Draft("emailaddress@mail.com", "Me");
//		Draft d2 = new Draft("emailaddress2@mail.com", "Iren");
//		Draft d3 = new Draft("emailaddres32@mail.com", "Iren11");
//
//		data.add(d1);
//		data.add(d2);
//		data.add(d3);
//
//		adapter = new DraftsAdapter(this, data);
//		lvDrafts.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		int id = item.getItemId();
		if (id == R.id.action_add_new_message) {
			intent = new Intent(this, ShareActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void fillListView() {
		String[] from = new String[] { DraftDBHelper.COLUMN_EMAIL,
				DraftDBHelper.COLUMN_SUBJECT };
		int[] to = new int[] { R.id.tvItemAddress, R.id.tvItemSubject };

		getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {

			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				String[] projection = { DraftDBHelper.COLUMN_ID,
						DraftDBHelper.COLUMN_EMAIL,
						DraftDBHelper.COLUMN_SUBJECT, DraftDBHelper.COLUMN_BODY };
				CursorLoader cursorLoader = new CursorLoader(MainActivity.this,
						DraftsProvider.CONTENT_URI, projection, null, null,
						null);
				return cursorLoader;
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
				adapter.swapCursor(data);
			}

			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				adapter.swapCursor(null);
			}
		});
		
		adapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
	}
}
