package fr.camillebriand.punkappchien.async;

import android.content.Context;
import android.os.AsyncTask;

import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.persistence.BeerDatabase;

public class InsertBeerToDatabaseTask extends AsyncTask<Beer, Void, Void> {
	private Context context;
	
	public InsertBeerToDatabaseTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Beer... beers) {
		BeerDatabase.getInstance(context).beerDAO().insertBeer(beers[0]);
		return null;
	}
}
