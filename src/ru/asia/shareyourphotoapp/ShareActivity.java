package ru.asia.shareyourphotoapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.asia.shareyourphotoapp.model.Draft;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ShareActivity extends ActionBarActivity {

	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_IMAGE_SELECT = 2;

	private Context context = this;
	private ImageButton btnAddPhoto;
	private EditText etEmail;
	private EditText etSubject;
	private EditText etBody;
	private Button btnShare;

	private String currentPhotoPath;
	long idFromIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

		btnAddPhoto = (ImageButton) findViewById(R.id.btnAddPhoto);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSubject = (EditText) findViewById(R.id.etSubject);
		etBody = (EditText) findViewById(R.id.etBody);
		btnShare = (Button) findViewById(R.id.btnShare);

		idFromIntent = getIntent().getLongExtra("idDraft", 0);

		if (idFromIntent != 0) {
			Draft editDraft = ShareYourPhotoApplication.getDataSource()
					.getDraft(idFromIntent);
			byte[] photoArray = editDraft.getPhoto();
			btnAddPhoto.setImageBitmap(BitmapFactory.decodeByteArray(
					photoArray, 0, photoArray.length));
			etEmail.setText(editDraft.getEmail());
			etSubject.setText(editDraft.getSubject());
			etBody.setText(editDraft.getBody());
		}

		btnAddPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				final Dialog addPhotoDialog = new Dialog(context,
						R.style.CustomDialogTheme);
				addPhotoDialog.setContentView(R.layout.add_photo_dialog);

				Button btnCamera = (Button) addPhotoDialog
						.findViewById(R.id.btnCamera);
				Button btnGallery = (Button) addPhotoDialog
						.findViewById(R.id.btnGallery);
				Button btnCancel = (Button) addPhotoDialog
						.findViewById(R.id.btnCancel);

				btnCamera.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						takePhotoByCamera();
						addPhotoDialog.dismiss();
					}
				});

				btnGallery.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						takePhotoFromGallery();
						addPhotoDialog.dismiss();
					}
				});

				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						addPhotoDialog.dismiss();
					}
				});

				addPhotoDialog.show();
			}
		});

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
					openEmailClient();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE
				&& currentPhotoPath != null) {
			setPic();
			currentPhotoPath = null;
		}
		if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_SELECT) {
			Uri selectedImageUri = data.getData();
			currentPhotoPath = getPath(selectedImageUri);
			setPic();
		}
	}

	private void fillData() {

		// if (uri != null) {
		// Log.e("----------", "fillData uri " + uri);
		// String[] projection = { DraftDBHelper.COLUMN_EMAIL,
		// DraftDBHelper.COLUMN_SUBJECT, DraftDBHelper.COLUMN_BODY };
		// Cursor cursor = getContentResolver().query(uri, projection, null,
		// null, null);
		// Log.e("----------",
		// "cursor fill data "
		// + cursor.getString(cursor
		// .getColumnIndex(DraftDBHelper.COLUMN_EMAIL)));
		// etEmail.setText(cursor.getString(cursor
		// .getColumnIndexOrThrow(DraftDBHelper.COLUMN_EMAIL)));
		// etSubject.setText(cursor.getString(cursor
		// .getColumnIndexOrThrow(DraftDBHelper.COLUMN_SUBJECT)));
		// etBody.setText(cursor.getString(cursor
		// .getColumnIndexOrThrow(DraftDBHelper.COLUMN_BODY)));
		// cursor.close();
		// }
	}

	private void saveData() {

		Bitmap image = ((BitmapDrawable) btnAddPhoto.getDrawable()).getBitmap();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 0, bos);
		byte[] photoArray = bos.toByteArray();
		String email = etEmail.getText().toString();
		String subject = etSubject.getText().toString();
		String body = etBody.getText().toString();

		if (idFromIntent != 0) {
			ShareYourPhotoApplication.getDataSource().updateContact(
					idFromIntent, photoArray, email, subject, body);
		} else {
			long id = ShareYourPhotoApplication.getDataSource().addContact(
					photoArray, email, subject, body);
		}
	}
	
	private void openEmailClient() {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String email = etEmail.getText().toString();
		String[] emailArray = new String[]{email};
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailArray);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, etSubject.getText().toString());
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, etBody.getText().toString());
		startActivity(Intent.createChooser(emailIntent, "Send email..."));
	}

	private void takePhotoByCamera() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
				currentPhotoPath = photoFile.getAbsolutePath();
			} catch (IOException e) {
				// Error occurred while creating the File
				e.printStackTrace();
				photoFile = null;
				currentPhotoPath = null;
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss")
				.format(new Date());
		String imageFile = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFile, ".jpg", storageDir);
		// Save a file: path for use with ACTION_VIEW intents
		currentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	private void takePhotoFromGallery() {
		Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
		galleryIntent.setType("image/*");
		startActivityForResult(
				Intent.createChooser(galleryIntent, "Select Picture"),
				REQUEST_IMAGE_SELECT);
	}

	private void setPic() {
		// Get the dimensions of the View
		int targetWidth = btnAddPhoto.getWidth();
		int targetHeight = btnAddPhoto.getHeight();

		Log.e("--------------", "target " + targetHeight + " " + targetWidth);

		// Get the dimension of the bitmap
		BitmapFactory.Options bfOptions = new BitmapFactory.Options();
		bfOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(currentPhotoPath, bfOptions);
		int photoWidth = bfOptions.outWidth;
		int photoHeigth = bfOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoWidth / targetWidth, photoHeigth
				/ targetHeight);

		// Decode the image file into a Bitmap sized to fill the View
		bfOptions.inJustDecodeBounds = false;
		bfOptions.inSampleSize = scaleFactor;
		bfOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bfOptions);
		btnAddPhoto.setImageBitmap(bitmap);
	}

	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = this.getContentResolver().query(uri, projection, null,
				null, null);
		int columnIndex = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(columnIndex);
	}
}
