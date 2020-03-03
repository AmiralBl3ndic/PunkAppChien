package fr.camillebriand.punkappchien.net;

import android.app.Activity;
import android.graphics.Bitmap;
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
	
	private WeakReference<Activity> activityRef;
	
	/**
	 * Create a new task to fetch the PunkAPI
	 * @param activity Activity to reference
	 */
	public FetchPunkAPITask(Activity activity) {
		this.activityRef = new WeakReference<>(activity);
	}
	
	@Override
	protected Beer doInBackground(Void... voids) {
		URL apiUrl;
		
		InputStream apiInputStream = null;
		InputStream imageInputStream = null;
		JSONObject jsonApiResponse;

		Beer beer = null;
		
		try {
			// Setup API connection
			apiUrl = new URL(RANDOM_BEER_API_PATH);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			
			try {
				// Get JSON response from API as InputStream
				apiInputStream = new BufferedInputStream(connection.getInputStream());
				
				// Convert InputStream response to String (easier to handle)
				String apiResponseAsString = StreamsUtil.readStream(apiInputStream);
				
				JSONArray apiResponse = new JSONArray(apiResponseAsString);
				
				if (apiResponse.length() == 0) {
					throw new RuntimeException("No data returned from API");
				}
				
				jsonApiResponse = apiResponse.getJSONObject(0);
				imageInputStream = new URL(jsonApiResponse.getString("image_url")).openStream();

				jsonApiResponse.put("image", BitmapFactory.decodeStream(imageInputStream));

				try {
					beer = new Beer(jsonApiResponse, this.activityRef.get().getApplicationContext());
				} catch (NullPointerException e) {  // Can only be thrown if activityRef.get() is null
					beer = new Beer(
							jsonApiResponse.getString("name"),
							jsonApiResponse.getString("description")
					);
					Bitmap bmp = BitmapFactory.decodeStream(imageInputStream);
					beer.setImage(bmp);
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
		
		MainActivity activity = (MainActivity) this.activityRef.get();
		
		if (activity == null) {
			Log.e("net", "Cannot process null activity");
			return;
		}
		
		BeerDialog beerDialog = activity.getBeerDialog();

		if (beer == null) {
			beerDialog.setBeerDescription("An error occurred, please try again");
			return;
		}

		beerDialog.setBeerName(beer.getName());
		beerDialog.setBeerDescription(beer.getDescription());
		beerDialog.setBeerImage(beer.getImage());
	}
}
