package fr.camillebriand.punkappchien.async;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;

import fr.camillebriand.punkappchien.BeerListAdapter;

public class GetBeerImageForListView extends GetBeerImageTask {
    private WeakReference<BeerListAdapter> activityRef;

    public GetBeerImageForListView(BeerListAdapter activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (this.activityRef == null || this.activityRef.get() == null) return;

        if (bitmap != null) {
            this.activityRef.get().getBeerImageView().setImageBitmap(bitmap);
        }
    }
}
