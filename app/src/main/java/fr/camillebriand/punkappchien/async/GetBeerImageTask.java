package fr.camillebriand.punkappchien.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import fr.camillebriand.punkappchien.model.Beer;

public abstract class GetBeerImageTask extends AsyncTask<Beer, Void, Bitmap> {
	@Override
	protected Bitmap doInBackground(Beer... beers) {
		if (beers == null) return null;
		Beer beer = beers[0];
		
		return beer.getImage();
	}
}
