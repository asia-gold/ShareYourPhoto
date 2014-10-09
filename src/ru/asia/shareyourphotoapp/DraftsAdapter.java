package ru.asia.shareyourphotoapp;

import java.util.ArrayList;

import ru.asia.shareyourphotoapp.model.Draft;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
	public void notifyDataSetChanged() {
		draftsList = ShareYourPhotoApplication.getDataSource().getAllDrafts();
		super.notifyDataSetChanged();
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
	
	static class ViewHolder {
		public TextView tvItemAddress;
		public TextView tvItemSubject;
		public ImageView ivItemPhoto;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item, parent, false);
			ViewHolder holder = new ViewHolder();
			holder.tvItemAddress = (TextView) view.findViewById(R.id.tvItemAddress);
			holder.tvItemSubject = (TextView) view.findViewById(R.id.tvItemSubject);
			holder.ivItemPhoto = (ImageView) view.findViewById(R.id.ivItemPhoto);
			view.setTag(holder);
		}
		
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		
		Draft tmpDraft = (Draft) draftsList.get(position);
		
		viewHolder.tvItemAddress.setText(tmpDraft.getEmail());
		viewHolder.tvItemSubject.setText(tmpDraft.getSubject());
		
		byte[] photoArray = tmpDraft.getPhoto();
		viewHolder.ivItemPhoto.setImageBitmap(BitmapFactory.decodeByteArray(photoArray, 0, photoArray.length));
		
		return view;
	}
}
