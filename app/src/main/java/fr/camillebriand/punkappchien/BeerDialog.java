package fr.camillebriand.punkappchien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.app.DialogFragment;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.camillebriand.punkappchien.model.Beer;

public class BeerDialog extends DialogFragment {
	
	Context context;
	Vibrator vibrator;
	
	private TextView beerName;
	private ImageView beerImage;
	private TextView beerDescription;
	private ProgressBar spinner;
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View dialogView = inflater.inflate(R.layout.beer_dialog, null);
		
		this.beerName = dialogView.findViewById(R.id.beer_dialog__name);
		this.beerImage = dialogView.findViewById(R.id.beer_dialog__image);
		this.beerDescription = dialogView.findViewById(R.id.beer_dialog__description);
		this.spinner = dialogView.findViewById(R.id.beer_dialog__spinner);
		
		this.vibrator = this.context == null ? null : (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		
		// Handle clicks on the dismiss button
		dialogView.findViewById(R.id.beer_dialog_dismiss_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		builder.setView(dialogView);
		return builder.create();
	}
	
	
	public void setBeer(Beer beer) {
		if (beer != null) {
			((ViewGroup) this.spinner.getParent()).removeView(this.spinner);
			
			this.setBeerName(beer.getName());
			this.setBeerImage(beer.getImage());
			this.setBeerDescription(beer.getDescription());
			
			if (this.vibrator != null) {
				this.vibrator.vibrate(200);
			}
		}
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
}
