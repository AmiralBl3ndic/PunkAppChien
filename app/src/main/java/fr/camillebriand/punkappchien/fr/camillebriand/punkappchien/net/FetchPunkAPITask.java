package fr.camillebriand.punkappchien.fr.camillebriand.punkappchien.net;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class FetchPunkAPITask extends AsyncTask {
	private static final String BASE_API_PATH = "https://api.punkapi.com/v2/beers";
	
	private static final String RANDOM_BEER_API_PATH = BASE_API_PATH + "/random";
	
	private WeakReference<Activity> activityRef;
	
	/**
	 * Create a new task to fetch the PunkAPI
	 * @param activity Activity to reference
	 */
	public FetchPunkAPITask(Activity activity) {
		this.activityRef = new WeakReference<>(activity);
	}
	
	@Override
	protected Object doInBackground(Object[] objects) {
		return null;
	}
}
