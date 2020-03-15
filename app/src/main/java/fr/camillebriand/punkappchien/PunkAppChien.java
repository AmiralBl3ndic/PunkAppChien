package fr.camillebriand.punkappchien;

import android.app.Application;

import fr.camillebriand.punkappchien.async.LoadBeersFromDatabaseTask;

public class PunkAppChien extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Load beers from local database into memory
		new LoadBeersFromDatabaseTask(getApplicationContext()).execute();
	}
}
