package ru.asia.shareyourphotoapp.dialogs;

import ru.asia.shareyourphotoapp.R;
import ru.asia.shareyourphotoapp.ShareActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Button;

/**
 * Build and show dialog for saving draft.
 * 
 * @author Asia
 *
 */
public class SaveDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(),
				R.style.CustomDialogTheme);
		AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
		builder.setMessage(getResources().getString(R.string.save_dialog))
				.setPositiveButton(R.string.str_save,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								((ShareActivity) getActivity()).saveData();
								((ShareActivity) getActivity()).finish();
								SaveDialogFragment.this.getDialog().cancel();
							}
						})
				.setNegativeButton(R.string.str_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								((ShareActivity) getActivity()).finish();
								SaveDialogFragment.this.getDialog().cancel();
							}
						});
		Dialog dialog = builder.create();
		dialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				Button cancelButton = ((AlertDialog) dialog)
						.getButton(DialogInterface.BUTTON_NEGATIVE);
				Button saveButton = ((AlertDialog) dialog)
						.getButton(DialogInterface.BUTTON_POSITIVE);

				final Drawable cancelButtonDrawable = getResources()
						.getDrawable(R.drawable.btn_negative_effects);
				final Drawable saveButtonDrawable = getResources()
						.getDrawable(R.drawable.btn_positive_effects);
				if (Build.VERSION.SDK_INT >= 16) {
					cancelButton.setBackground(cancelButtonDrawable);
					saveButton.setBackground(saveButtonDrawable);
				} else {
					cancelButton
							.setBackgroundDrawable(cancelButtonDrawable);
					saveButton
							.setBackgroundDrawable(saveButtonDrawable);
				}

				cancelButton.invalidate();
				saveButton.invalidate();
			}
		});
		return dialog;
	}

}
