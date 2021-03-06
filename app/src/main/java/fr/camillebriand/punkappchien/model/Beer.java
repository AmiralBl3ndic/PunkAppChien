package fr.camillebriand.punkappchien.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
public class Beer implements Serializable {
	@Ignore @Getter @Setter(AccessLevel.NONE)
	private static final ArrayList<Beer> favourites = new ArrayList<>();
	
	@Ignore @Getter
	private static final ArrayList<Beer> dbBeers = new ArrayList<>();
	
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
	
	@ColumnInfo(name = "grade", typeAffinity = ColumnInfo.INTEGER)
	private int grade = 0;  // 0/10 by default
	
	@ColumnInfo(name = "is_favourite")
	private boolean isFavourite = false;

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
		} catch (JSONException e) {
			Log.e("punkappchien", "JSONException", e);
		}
	}
	///endregion
	
	public Bitmap getImage() {
		// Do not use network if not necessary
		if (imageUrl.equals("null")) return null;
		
		try {
			InputStream imageInputSteam = new URL(imageUrl).openStream();
			Bitmap image = BitmapFactory.decodeStream(imageInputSteam);
			imageInputSteam.close();
			return image;
		} catch (MalformedURLException e) {
			Log.e("punkappchien", "MalformedURLException", e);
		} catch (IOException e) {
			Log.e("punkappchien", "IOException", e);
		}
		
		return null;
	}
	
	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			Beer b = (Beer) obj;
			
			return b.name.equals(name) && b.tagline.equals(tagline) && b.ibu == ibu;
		}
		return false;
	}

	/**
	 * Add a beer to the collection of beers
	 * @param beer Beer to add to the favourite beers
	 */
	public static void addBeerToFavourites(Beer beer) {
		// Safety so that nulls or duplicates are not stored
		if (beer == null || favourites.contains(beer)) return;
		
		beer.isFavourite = true;
		favourites.add(beer);
	}
	
	/**
	 * Checks if a beer is marked as a favourite
	 * @param beer Beer to check
	 * @return Whether the beer was marked as favourite
	 */
	public static boolean isBeerFromAPIFavourite(Beer beer) {
		for (Beer b : favourites) {
			if (b.equals(beer)) return true;
		}
		
		return false;
	}
	
	///region Default Getters & Setters
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
	
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public boolean isFavourite() {
		return isFavourite;
	}
	
	public void setFavourite(boolean favourite) {
		isFavourite = favourite;
	}
	///endregion
}
