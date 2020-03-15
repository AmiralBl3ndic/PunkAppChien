package fr.camillebriand.punkappchien.async;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.persistence.BeerDatabase;

public class LoadBeersFromDatabaseTask extends AsyncTask<Void, Void, Void> {
	private WeakReference<Context> contextRef;
	
	public LoadBeersFromDatabaseTask(Context context) {
		this.contextRef = new WeakReference<>(context);
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		if (contextRef == null || contextRef.get() == null) return null;
		
		List<Beer> beers = BeerDatabase.getInstance(contextRef.get()).beerDAO().getBeers();
		
		for (Beer beer : beers) {
			Beer.getDbBeers().add(beer);
			if (beer.isFavourite()) {
				Beer.addBeerToFavourites(beer);
			}
		}
		
		return null;
	}
}
