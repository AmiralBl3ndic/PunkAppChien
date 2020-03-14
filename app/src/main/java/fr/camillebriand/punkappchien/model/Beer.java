package fr.camillebriand.punkappchien.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.camillebriand.punkappchien.R;
import lombok.Data;

/**
 * Represents the interesting beer data returned by the Punk API
 */
@Data
public class Beer {
	private static final String JSON_NAME_KEY = "name";
	private static final String JSON_DESCRIPTION_KEY = "description";
	private static final String JSON_IMAGE_URL_KEY = "image_url";

	private static final ArrayList<Beer> favourites = new ArrayList<>();

	private String name;

	private String description;

	private Bitmap image;

	/**
	 * Create a beer instance with no image
	 * @param name Name of the beer
	 * @param description Description of the beer
	 */
	public Beer(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Beer(String name, String description, Bitmap image) {
		this(name, description);
		this.image = image;
	}

	public Beer(Context context, String name, String description) {
		this(
				name,
				description,
				getDefaultImage(context)
		);
	}

	/**
	 * Instantiate a beer from a JSON object as returned by the Punk API
	 * @param jsonBeer JSON Object representing a beer. Must have {@code name}, {@code description} and {@code image_url} fields
	 * @param context Context in which the application is executed, mandatory to handle images not found
	 */
	public Beer(JSONObject jsonBeer, Context context) {
		if (jsonBeer == null) {
			throw new IllegalArgumentException("Cannot create a beer from a null object");
		}

		try {
			this.name = jsonBeer.getString(JSON_NAME_KEY);
			this.description = jsonBeer.getString(JSON_DESCRIPTION_KEY);
		} catch (JSONException e) {
			Log.e("punkappchien", "JSONException", e);
		}

		try {
			this.image = null;
			this.image = BitmapFactory.decodeStream(new URL(jsonBeer.getString(JSON_IMAGE_URL_KEY)).openStream());
		} catch (IOException e) {
			Log.e("punkappchien", "IOException", e);
		} catch (JSONException e) {
			Log.e("punkappchien", "JSONException", e);
		} finally {
			if (this.image == null) {
				this.image = getDefaultImage(context);
			}
		}
	}


	private static Bitmap getDefaultImage(Context context) {
		return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
	}

	/**
	 * Get the collection of beers
	 * @return A {@link List<Beer>} of beers
	 */
	public static List<Beer> getBeersCollection() {
		return favourites;
	}

	/**
	 * Add a beer to the collection of beers
	 * @param beer
	 */
	public static void addBeerToFavourites(Beer beer) {
		favourites.add(beer);
	}
}
