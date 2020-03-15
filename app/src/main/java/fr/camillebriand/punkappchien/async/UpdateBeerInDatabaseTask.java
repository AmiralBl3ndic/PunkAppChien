package fr.camillebriand.punkappchien.async;

import android.content.Context;

import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.persistence.BeerDatabase;

public class UpdateBeerInDatabaseTask extends BeerDatabaseTask {
	public UpdateBeerInDatabaseTask(Context context) {
		super(context);
	}
	
	@Override
	protected Void doInBackground(Beer... beers) {
		if (!Beer.getDbBeers().contains(beers[0])) {
			new InsertBeerToDatabaseTask(context).execute(beers[0]);
		} else {
			BeerDatabase.getInstance(context).beerDAO().updateBeer(beers[0]);
		}
		
		return null;
	}
}
