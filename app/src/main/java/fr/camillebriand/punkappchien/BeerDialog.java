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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.camillebriand.punkappchien.async.GetBeerImageForDialog;
import fr.camillebriand.punkappchien.async.InsertBeerToDatabaseTask;
import fr.camillebriand.punkappchien.async.UpdateBeerInDatabaseTask;
import fr.camillebriand.punkappchien.model.Beer;

public class BeerDialog extends DialogFragment {
	
	Activity activity;
	Context context;
	Vibrator vibrator;
	
	Beer beer = null;
	
	private TextView beerName;
	private ImageView beerImage;
	private TextView beerDescription;
	private ProgressBar spinner;
	private Button toggleFavouriteButton;
	
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
		this.toggleFavouriteButton = dialogView.findViewById(R.id.beer_dialog_toggle_favourite_button);
		
		this.toggleFavouriteButton.setText(Beer.getFavourites().contains(beer) ? R.string.remove_from_favourites : R.string.add_to_favourites);
		
		this.vibrator = this.context == null ? null : (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		
		// Handle clicks on the add to favourites button
		this.toggleFavouriteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Beer.getFavourites().contains(beer)) {
					toggleFavouriteButton.setText(R.string.add_to_favourites);
					
					beer.setFavourite(false);
					Beer.getFavourites().remove(beer);
				} else {
					toggleFavouriteButton.setText(R.string.remove_from_favourites);
					
					Beer.addBeerToFavourites(beer);
					beer.setFavourite(true);
				}
				
				new UpdateBeerInDatabaseTask(context).execute(beer);
			}
		});
		
		// Handle clicks on the more details button
		dialogView.findViewById(R.id.beer_dialog_more_details_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, BeerDetailsActivity.class);
				intent.putExtra("beer", beer);
				startActivity(intent);
			}
		});
		
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
