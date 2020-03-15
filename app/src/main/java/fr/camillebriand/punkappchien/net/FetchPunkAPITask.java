package fr.camillebriand.punkappchien.net;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.camillebriand.punkappchien.BeerDialog;
import fr.camillebriand.punkappchien.MainActivity;
import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.util.StreamsUtil;

public class FetchPunkAPITask extends AsyncTask<Void, Void, Beer> {
	private static final String BASE_API_PATH = "https://api.punkapi.com/v2/beers";
	
	private static final String RANDOM_BEER_API_PATH = BASE_API_PATH + "/random";
	private static final String BEER_API_BEER_NAME_FILTER = "beer_name=";
	
	private WeakReference<Activity> activityRef;
	
	private String beerName = "";
	
	private BeerDialog beerDialog;
	
	/**
	 * Create a new task to fetch the PunkAPI
	 * @param activity Activity to reference
	 */
	public FetchPunkAPITask(Activity activity) {
		this.activityRef = new WeakReference<>(activity);
		this.beerDialog = new BeerDialog();
	}
	
	public FetchPunkAPITask(Activity activity, String beerName) {
		this(activity);
		this.beerName = beerName.replace(" ", "_");
	}
	
	@Override
	protected Beer doInBackground(Void... voids) {
		if (activityRef == null || activityRef.get() == null) return null;
		
		// First, display the dialog
		Activity activity = activityRef.get();
		beerDialog.setActivity(activity);
		beerDialog.setContext(activity.getApplicationContext());
		beerDialog.show(activity.getFragmentManager(), "dialog");
		
		URL apiUrl;
		
		InputStream apiInputStream = null;
		InputStream imageInputStream = null;
		JSONObject jsonApiResponse;

		Beer beer = null;
		
		try {
			// Setup API connection
			if (this.beerName.isEmpty()) {
				apiUrl = new URL(RANDOM_BEER_API_PATH);
			} else {
				apiUrl = new URL(BASE_API_PATH + "?" + BEER_API_BEER_NAME_FILTER + this.beerName);
			}
			
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			
			try {
				// Get JSON response from API as InputStream
				apiInputStream = new BufferedInputStream(connection.getInputStream());
				
				// Convert InputStream response to String (easier to handle)
				String apiResponseAsString = StreamsUtil.readStream(apiInputStream);
				
				JSONArray apiResponse = new JSONArray(apiResponseAsString);
				
				if (apiResponse.length() == 0) {
					return null;
				}
				
				jsonApiResponse = apiResponse.getJSONObject(0);
				
				if (jsonApiResponse.has("image_url") && !jsonApiResponse.getString("image_url").equals("null")) {
					imageInputStream = new URL(jsonApiResponse.getString("image_url")).openStream();
					jsonApiResponse.put("image", BitmapFactory.decodeStream(imageInputStream));
				}
				
				try {
					beer = new Beer(jsonApiResponse);
				} catch (NullPointerException e) {  // Can only be thrown if activityRef.get() is null
					if (this.activityRef.get() != null) {
						beer = new Beer(jsonApiResponse);
					}
				}
			} catch (JSONException e) {
				Log.e("net", "JSONException", e);
			} catch (RuntimeException e) {
				Log.e("net", "RuntimeException", e);
			} finally {
				if (apiInputStream != null) {
					apiInputStream.close();
				}
				
				if (imageInputStream != null) {
					imageInputStream.close();
				}
				
				connection.disconnect();
				
			}
		} catch (IOException e) {
			Log.e("net", "IOException", e);
		}

		return beer;
	}
	
	@Override
	protected void onPostExecute(Beer beer) {
		if (this.activityRef == null) {
			Log.e("net", "Cannot process null weak reference to activity");
			return;
		}
		
		
		if (this.activityRef.get() == null) {
			Log.e("net", "Cannot process null activity");
			return;
		}
		
		if (beer == null) {  // If no beer returned, try again with no parameters
			new FetchPunkAPITask(this.activityRef.get()).execute();
			return;
		}
		
		this.beerDialog.setBeer(beer);  // Update BeerDialog view
	}
}
