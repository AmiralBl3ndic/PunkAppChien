package fr.camillebriand.punkappchien.async;

import android.content.Context;
import android.os.AsyncTask;

import fr.camillebriand.punkappchien.model.Beer;

abstract class BeerDatabaseTask extends AsyncTask<Beer, Void, Void> {
	protected Context context;
	
	protected BeerDatabaseTask(Context context) {
		this.context = context;
	}
}
