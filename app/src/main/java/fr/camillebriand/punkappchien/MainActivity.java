package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.net.FetchPunkAPITask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.findViewById(R.id.main_activity_dialog_button).setOnClickListener(this);

		new FetchPunkAPITask(this).execute();

		Button favoris = findViewById(R.id.to_favoris);
		favoris.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent favorisActivity = new Intent(getApplicationContext(),FavorisActivity.class);
				startActivity(favorisActivity);
				finish();
			}
		});
	}
	@Override
	public void onClick(View v) {
		ListView beerList = findViewById(R.id.shop_list_view);
		beerList.setAdapter(new BeerListAdapter(this, Beer.getDbBeers()));

		EditText searchBeer = (EditText) findViewById(R.id.beerSearched);
		String searchBeerStr = searchBeer.getText().toString();

		if(searchBeerStr.isEmpty())
		{
			new FetchPunkAPITask(this).execute();
		}
		else new FetchPunkAPITask(this, searchBeerStr).execute();


	}
}
