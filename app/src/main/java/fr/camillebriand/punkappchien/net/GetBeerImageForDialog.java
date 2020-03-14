package fr.camillebriand.punkappchien.net;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;

import fr.camillebriand.punkappchien.BeerDialog;

public final class GetBeerImageForDialog extends GetBeerImageTask {
	private WeakReference<BeerDialog> dialogWeakReference;
	
	public GetBeerImageForDialog(BeerDialog beerDialog) {
		this.dialogWeakReference = new WeakReference<>(beerDialog);
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (this.dialogWeakReference == null || this.dialogWeakReference.get() == null) return;
		
		this.dialogWeakReference.get().setBeerImage(bitmap);
	}
}
