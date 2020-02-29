package fr.camillebriand.punkappchien.fr.camillebriand.punkappchien.net;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.camillebriand.punkappchien.fr.camillebriand.punkappchien.util.StreamsUtil;

public class FetchPunkAPITask extends AsyncTask<Void, Void, JSONObject> {
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
	protected JSONObject doInBackground(Void... voids) {
		URL apiUrl;
		
		try {
			// Setup API connection
			apiUrl = new URL(RANDOM_BEER_API_PATH);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			
			try {
				// Get JSON response from API
				InputStream is = new BufferedInputStream(connection.getInputStream());
			} finally {
				connection.disconnect();
			}
		} catch (IOException e) {
			Log.e("net", "IOException", e);
		}
		
		return null;
	}
}
