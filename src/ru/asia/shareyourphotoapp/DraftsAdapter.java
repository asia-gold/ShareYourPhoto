package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.model.Draft;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DraftsAdapter extends BaseAdapter {
	
	private Activity draftsActivity;
	private ArrayList<Draft> draftsList;
	private static LayoutInflater inflater;
	
	public DraftsAdapter(Activity activity, ArrayList<Draft> data) {
		draftsActivity = activity;
		draftsList = data;
		inflater = (LayoutInflater) draftsActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return draftsList.size();
	}

	@Override
	public Object getItem(int position) {
		return draftsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item, parent, false);
		}
		
		TextView tvItemAddress = (TextView) view.findViewById(R.id.tvItemAddress);
		TextView tvItemSubject = (TextView) view.findViewById(R.id.tvItemSubject);
		
		Draft tmpDraft = (Draft) draftsList.get(position);
		
		tvItemAddress.setText(tmpDraft.getEmail());
		tvItemSubject.setText(tmpDraft.getSubject());
		
		return view;
	}
}
