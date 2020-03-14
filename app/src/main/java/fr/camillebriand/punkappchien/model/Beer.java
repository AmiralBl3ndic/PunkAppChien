package fr.camillebriand.punkappchien.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the interesting beer data returned by the Punk API
 */
@Entity(tableName = "Beers", primaryKeys = {"name", "tagline"})
public class Beer {
	@Ignore @Getter @Setter(AccessLevel.NONE)
	private static final ArrayList<Beer> favourites = new ArrayList<>();
	
	@ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT) @NonNull
	private String name = "";
	
	@ColumnInfo(name = "tagline", typeAffinity = ColumnInfo.TEXT) @NonNull
	private String tagline = "";

	@ColumnInfo(name = "description", typeAffinity = ColumnInfo.TEXT)
	private String description;
	
	@ColumnInfo(name = "image_url", typeAffinity = ColumnInfo.TEXT)
	private String imageUrl;
	
	@ColumnInfo(name = "ibu", typeAffinity = ColumnInfo.INTEGER)
	private int ibu;
	
	@ColumnInfo(name = "abv", typeAffinity = ColumnInfo.REAL)
	private double abv;
	
	@Ignore
	private Bitmap image;

	///region Constructors
	public Beer() {}
	
	/**
	 * Instantiate a beer from a JSON object as returned by the Punk API
	 * @param jsonBeer JSON Object representing a beer. Must have {@code name}, {@code description} and {@code image_url} fields
	 */
	public Beer(JSONObject jsonBeer) {
		if (jsonBeer == null) {
			throw new IllegalArgumentException("Cannot create a beer from a null object");
		}

		try {
			this.name = jsonBeer.getString("name");
			this.description = jsonBeer.getString("description");
			this.imageUrl = jsonBeer.getString("image_url");
			this.tagline = jsonBeer.getString("tagline");
			this.abv = jsonBeer.getDouble("abv");
			this.ibu = jsonBeer.getInt("ibu");
			
			if (this.imageUrl.equals("null")) {
				this.image = null;
			} else {
				loadImageFromUrl();
			}
		} catch (JSONException e) {
			Log.e("punkappchien", "JSONException", e);
		}
	}
	///endregion
	
	private void loadImageFromUrl() {
		// Do not use network if not necessary
		if (imageUrl.equals("null")) return;
		
		try {
			InputStream imageInputSteam = new URL(imageUrl).openStream();
			image = BitmapFactory.decodeStream(imageInputSteam);
			imageInputSteam.close();
		} catch (MalformedURLException e) {
			Log.e("punkappchien", "MalformedURLException", e);
		} catch (IOException e) {
			Log.e("punkappchien", "IOException", e);
		}
	}
	

	/**
	 * Add a beer to the collection of beers
	 * @param beer Beer to add to the favourite beers
	 */
	public static void addBeerToFavourites(Beer beer) {
		// Safety so that nulls or duplicates are not stored
		if (beer == null || favourites.contains(beer)) return;
		
		favourites.add(beer);
	}
	
	///region Getters & Setters
	@NonNull
	public String getName() {
		return name;
	}
	
	public void setName(@NonNull String name) {
		this.name = name;
	}
	
	@NonNull
	public String getTagline() {
		return tagline;
	}
	
	public void setTagline(@NonNull String tagline) {
		this.tagline = tagline;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public int getIbu() {
		return ibu;
	}
	
	public void setIbu(int ibu) {
		this.ibu = ibu;
	}
	
	public double getAbv() {
		return abv;
	}
	
	public void setAbv(double abv) {
		this.abv = abv;
	}
	
	public Bitmap getImage() {
		return image;
	}
	///endregion
}
