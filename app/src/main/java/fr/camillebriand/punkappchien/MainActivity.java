package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import fr.camillebriand.punkappchien.net.FetchPunkAPITask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	
	private BeerDialog beerDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.beerDialog = new BeerDialog();
		this.beerDialog.setContext(getApplicationContext());
		this.beerDialog.setActivity(this);
		
		showBeerDialog();
		
		this.findViewById(R.id.main_activity_dialog_button).setOnClickListener(this);
		
		new FetchPunkAPITask(this).execute();
	}
	
	public BeerDialog getBeerDialog() {
		return this.beerDialog;
	}
	
	/**
	 * Shows the beer dialog
	 */
	public void showBeerDialog() {
		this.beerDialog.show(getFragmentManager(), "dialog");
	}
	
	@Override
	public void onClick(View v) {
		new FetchPunkAPITask(this).execute();
		this.showBeerDialog();
	}
}
