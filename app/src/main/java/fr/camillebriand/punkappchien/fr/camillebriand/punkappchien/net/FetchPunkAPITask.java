package fr.camillebriand.punkappchien.fr.camillebriand.punkappchien.net;

import android.os.AsyncTask;

public class FetchPunkAPITask extends AsyncTask {
	private static final String BASE_API_PATH = "https://api.punkapi.com/v2/beers";
	
	private static final String RANDOM_BEER_API_PATH = BASE_API_PATH + "/random";
	
	@Override
	protected Object doInBackground(Object[] objects) {
		return null;
	}
}
