package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	
	private BeerDialog beerDialog = new BeerDialog();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showBeerDialog();
	}
	
	/**
	 * Shows the beer dialog
	 */
	private void showBeerDialog() {
		this.beerDialog.show(getFragmentManager(), "dialog");
	}
}
