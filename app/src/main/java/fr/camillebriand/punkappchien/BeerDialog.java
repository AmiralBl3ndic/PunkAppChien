package fr.camillebriand.punkappchien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.app.DialogFragment;

public class BeerDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setView(R.layout.beer_dialog);
		
		return builder.create();
	}
}
