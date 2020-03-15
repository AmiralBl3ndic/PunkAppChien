package fr.camillebriand.punkappchien.async;

import android.content.Context;

import fr.camillebriand.punkappchien.model.Beer;

public class LoadBeersFromDatabaseTask extends BeerDatabaseTask {
	protected LoadBeersFromDatabaseTask(Context context) {
		super(context);
	}
	
	@Override
	protected Void doInBackground(Beer... beers) {
		
		
		return null;
	}
}
