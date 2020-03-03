package fr.camillebriand.punkappchien.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import fr.camillebriand.punkappchien.R;

/**
 * Represents the interesting beer data returned by the Punk API
 */
public class Beer {
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
				BitmapFactory.decodeResource(context.getResources(), R.drawable.logo)
		);
	}
}
