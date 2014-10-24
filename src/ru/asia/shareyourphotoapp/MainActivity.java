package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.dialogs.RemoveAllDialogFragment;
import ru.asia.shareyourphotoapp.dialogs.RemoveItemDialogFragment;
import ru.asia.shareyourphotoapp.model.Draft;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
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

/**
 * Activity allows User to review drafts or sent messages.
 * 
 * @author Asia
 *
 */
public class MainActivity extends ActionBarActivity {

	//private Context context = this;
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
			public boolean onItemLongClick(AdapterView<?> parent, View view,
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
		RemoveItemDialogFragment removeItemDialog = new RemoveItemDialogFragment(deleteDraft);
		removeItemDialog.show(getFragmentManager(), "dialog");
	}

	/**
	 * Show dialog to remove all data.
	 */
	private void showRemoveAllDialog() {
		RemoveAllDialogFragment removeAllDialog = new RemoveAllDialogFragment();
		removeAllDialog.show(getFragmentManager(), "dialog");
	}

	@Override
	protected void onResume() {
		updateData();
		super.onResume();
	}
	
	public void updateData() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
}
