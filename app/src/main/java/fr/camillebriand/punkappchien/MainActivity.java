package fr.camillebriand.punkappchien;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import fr.camillebriand.punkappchien.net.FetchPunkAPITask;

import static fr.camillebriand.punkappchien.model.Beer.dbBeers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.findViewById(R.id.main_activity_dialog_button).setOnClickListener(this);

		new FetchPunkAPITask(this).execute();
	}
	@Override
	public void onClick(View v) {
		ListView beerList = findViewById(R.id.shop_list_view);
		beerList.setAdapter(new BeerListAdapter(this, dbBeers));

		new FetchPunkAPITask(this).execute();

		EditText searchBeer = (EditText) findViewById(R.id.beerSearched);
		String searchBeerStr = searchBeer.getText().toString();

		new FetchPunkAPITask(this, searchBeerStr).execute();

	}

	public void onClick2(View v) {
		//EditText searchBeer = (EditText) findViewById(R.id.beerSearched);
		//String searchBeerStr = searchBeer.getText().toString();
		//new FetchPunkAPITask(this, searchBeerStr).execute();
	}
}
