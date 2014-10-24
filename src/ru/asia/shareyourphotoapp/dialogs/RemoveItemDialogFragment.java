package ru.asia.shareyourphotoapp.dialogs;

import ru.asia.shareyourphotoapp.MainActivity;
import ru.asia.shareyourphotoapp.R;
import ru.asia.shareyourphotoapp.ShareYourPhotoApplication;
import ru.asia.shareyourphotoapp.model.Draft;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

/**
 * Build and show dialog for removing selected item.
 * 
 * @author Asia
 *
 */
public class RemoveItemDialogFragment extends DialogFragment{
	
	Draft deleteDraft;
	
	public RemoveItemDialogFragment(Draft draft) {
		deleteDraft = draft;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme);
		AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
		builder.setMessage(
				getResources().getString(R.string.remove_all_dialog))
				.setPositiveButton(R.string.str_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								ShareYourPhotoApplication.getDataSource().deleteDraft(
										deleteDraft);
								 ((MainActivity) getActivity()).updateData();
								 RemoveItemDialogFragment.this.getDialog()
									.cancel();
							}
						})
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								RemoveItemDialogFragment.this.getDialog()
										.cancel();
							}
						});
		return builder.create();
	}
}
