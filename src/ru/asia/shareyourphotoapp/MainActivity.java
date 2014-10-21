package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.model.Draft;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private Context context = this;
	private ListView lvDrafts;
	private Button btnRemoveAll;
	private ArrayList<Draft> data;
	private DraftsAdapter adapter;
	
	Draft deleteDraft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvDrafts = (ListView) findViewById(R.id.lvDrafts);
		btnRemoveAll = (Button) findViewById(R.id.btnRemoveAll);
		
		data = ShareYourPhotoApplication.getDataSource().getAllDrafts();
		adapter = new DraftsAdapter(this, data);

		lvDrafts.setAdapter(adapter);
		lvDrafts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Draft draft = (Draft) lvDrafts.getAdapter().getItem(position);
				long itemId = draft.getId();
				Log.e("-------", "item id " + itemId);
				
				Intent intent = new Intent(MainActivity.this,
						ShareActivity.class);
				intent.putExtra("idDraft", itemId);
				startActivity(intent);
			}
		});
		
		lvDrafts.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?>parent, View view,
					int position, long id) {
				
				deleteDraft = (Draft) lvDrafts.getAdapter().getItem(position);
				showRemoveItemDialog();				
				return false;
			}
			
		});

		btnRemoveAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				showRemoveAllDialog();				
			}
		});
	}
	
	/**
	 * Show dialog to remove selected draft.
	 */
	private void showRemoveItemDialog() {
		final Dialog removeItemDialog = new Dialog(context, R.style.CustomDialogTheme);
		removeItemDialog.setContentView(R.layout.remove_dialog);
		TextView tvMessage = (TextView) removeItemDialog
				.findViewById(R.id.tvMessege);
		Button btnNo = (Button) removeItemDialog.findViewById(R.id.btnNo);
		Button btnYes = (Button) removeItemDialog.findViewById(R.id.btnYes);

		tvMessage.setText(getResources().getString(
				R.string.remove_dialog));				

		btnNo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				removeItemDialog.dismiss();
			}
		});

		btnYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {						
				ShareYourPhotoApplication.getDataSource().deleteDraft(deleteDraft);
				adapter.notifyDataSetChanged();
				removeItemDialog.dismiss();
			}
		});

		removeItemDialog.show();
	}
	
	/**
	 * Show dialog to remove all data. 
	 */
	private void showRemoveAllDialog(){
		final Dialog removeAllDialog = new Dialog(context, R.style.CustomDialogTheme);
		removeAllDialog.setContentView(R.layout.remove_dialog);
		TextView tvMessage = (TextView) removeAllDialog
				.findViewById(R.id.tvMessege);
		Button btnNo = (Button) removeAllDialog.findViewById(R.id.btnNo);
		Button btnYes = (Button) removeAllDialog.findViewById(R.id.btnYes);

		tvMessage.setText(getResources().getString(
				R.string.remove_all_dialog));

		btnNo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				removeAllDialog.dismiss();
			}
		});

		btnYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				ShareYourPhotoApplication.getDataSource().deleteAllDrafts();
				adapter.notifyDataSetChanged();
				removeAllDialog.dismiss();
			}
		});

		removeAllDialog.show();
	}
	
	@Override
	protected void onResume() {
		adapter.notifyDataSetChanged();
		super.onResume();
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

//	private void fillListView() {
//		String[] from = new String[] { DraftDBHelper.COLUMN_EMAIL,
//				DraftDBHelper.COLUMN_SUBJECT };
//		int[] to = new int[] { R.id.tvItemAddress, R.id.tvItemSubject };
//
//		getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {
//
//			@Override
//			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//				String[] projection = { DraftDBHelper.COLUMN_ID,
//						DraftDBHelper.COLUMN_EMAIL,
//						DraftDBHelper.COLUMN_SUBJECT, DraftDBHelper.COLUMN_BODY };
//				CursorLoader cursorLoader = new CursorLoader(MainActivity.this,
//						DraftsProvider.CONTENT_URI, projection, null, null,
//						null);
//				return cursorLoader;
//			}
//
//			@Override
//			public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//				adapter.swapCursor(data);
//			}
//
//			@Override
//			public void onLoaderReset(Loader<Cursor> loader) {
//				adapter.swapCursor(null);
//			}
//		});
//
//		adapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to,
//				0);
//	}
}
