package fr.camillebriand.punkappchien.async;

import android.content.Context;

import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.persistence.BeerDatabase;

public class InsertBeerToDatabaseTask extends BeerDatabaseTask {
	public InsertBeerToDatabaseTask(Context context) {
		super(context);
	}
	
	@Override
	protected Void doInBackground(Beer... beers) {
		BeerDatabase.getInstance(context).beerDAO().insertBeer(beers[0]);
		return null;
	}
}
