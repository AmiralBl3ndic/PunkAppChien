package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;

import android.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	
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
		new BeerDialog().show(getFragmentManager(), "dialog");
	}
}
