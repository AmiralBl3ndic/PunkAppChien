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
		
		
		FragmentManager fm = getFragmentManager();
		DialogFragment beerDialog = new BeerDialog();
		beerDialog.show(fm, "dialog");
	}
}
