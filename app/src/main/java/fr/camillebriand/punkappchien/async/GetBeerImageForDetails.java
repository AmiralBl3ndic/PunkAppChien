package fr.camillebriand.punkappchien.async;

import android.app.Activity;
import android.graphics.Bitmap;

import java.lang.ref.WeakReference;

import fr.camillebriand.punkappchien.BeerDetailsActivity;

public class GetBeerImageForDetails extends GetBeerImageTask {
	private WeakReference<BeerDetailsActivity> activityRef;
	
	public GetBeerImageForDetails(BeerDetailsActivity activity) {
		this.activityRef = new WeakReference<>(activity);
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (this.activityRef == null || this.activityRef.get() == null) return;
		
		this.activityRef.get().getBeerImageView().setImageBitmap(bitmap);
	}
}
