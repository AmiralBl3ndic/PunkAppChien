package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.camillebriand.punkappchien.net.FetchPunkAPITask;

public class MainActivity extends AppCompatActivity {
	
	private BeerDialog beerDialog = new BeerDialog();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showBeerDialog();
		
		new FetchPunkAPITask(this).execute();
	}
	
	public BeerDialog getBeerDialog() {
		return this.beerDialog;
	}
	
	/**
	 * Shows the beer dialog
	 */
	private void showBeerDialog() {
		this.beerDialog.show(getFragmentManager(), "dialog");
	}
}
