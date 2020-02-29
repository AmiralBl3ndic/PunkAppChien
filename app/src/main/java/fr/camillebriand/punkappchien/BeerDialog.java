package fr.camillebriand.punkappchien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BeerDialog extends DialogFragment implements View.OnClickListener {
	
	private TextView beerName;
	private ImageView beerImage;
	private TextView beerDescription;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View dialogView = inflater.inflate(R.layout.beer_dialog, null);
		
		this.beerName = dialogView.findViewById(R.id.beer_dialog__name);
		this.beerImage = dialogView.findViewById(R.id.beer_dialog__image);
		this.beerDescription = dialogView.findViewById(R.id.beer_dialog__description);
		
		dialogView.findViewById(R.id.beer_dialog_dismiss_button).setOnClickListener(this);
		
		builder.setView(dialogView);
		return builder.create();
	}
	
	public void setBeerName(String beerName) {
		if (this.beerName == null) return;
		
		this.beerName.setText(beerName);
	}
	
	public void setBeerImage(Bitmap bmp) {
		if (this.beerImage == null) return;
		
		this.beerImage.setImageBitmap(bmp);
	}
	
	public void setBeerDescription(String beerDescription) {
		if (this.beerDescription == null) return;
		
		this.beerDescription.setText(beerDescription);
	}
	
	@Override
	public void onClick(View v) {
		this.dismiss();
	}
}
