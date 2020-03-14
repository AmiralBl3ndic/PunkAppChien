package fr.camillebriand.punkappchien;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.net.GetBeerImageForDialog;
import fr.camillebriand.punkappchien.persistence.BeerDatabase;

public class BeerDialog extends DialogFragment {
	
	Activity activity;
	Context context;
	Vibrator vibrator;
	
	Beer beer = null;
	
	private TextView beerName;
	private ImageView beerImage;
	private TextView beerDescription;
	private ProgressBar spinner;
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void setActivity(Activity activity) {
		this.activity = activity;
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
				Intent detailsIntent = new Intent(activity, BeerDetailsActivity.class);
				detailsIntent.putExtra("beer", beer);
				startActivity(detailsIntent);
				dismiss();
			}
		});
		
		dialogView.findViewById(R.id.beer_dialog_add_favourite_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Beer.addBeerToFavourites(beer);  // Local "caching"
				BeerDatabase.getInstance(context).beerDAO().insertBeer(beer);  // Persistence
				dismiss();
			}
		});
		
		builder.setView(dialogView);
		return builder.create();
	}
	
	
	public void setBeer(Beer beer) {
		this.beer = beer;
		
		if (beer != null) {
			((ViewGroup) this.spinner.getParent()).removeView(this.spinner);
			
			this.setBeerName(beer.getName());
			this.setBeerDescription(beer.getDescription());
			new GetBeerImageForDialog(this).execute(beer);
			
			if (this.vibrator != null) {
				this.vibrator.vibrate(200);
			}
		}
	}
	
	private void setBeerName(String beerName) {
		if (this.beerName == null) return;
		
		this.beerName.setText(beerName);
	}
	
	public void setBeerImage(Bitmap bmp) {
		if (this.beerImage == null) return;
		
		this.beerImage.setImageBitmap(bmp);
	}
	
	private void setBeerDescription(String beerDescription) {
		if (this.beerDescription == null) return;
		
		this.beerDescription.setText(beerDescription);
	}
}
