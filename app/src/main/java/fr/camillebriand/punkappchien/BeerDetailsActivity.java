package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import fr.camillebriand.punkappchien.async.GetBeerImageForDetails;
import fr.camillebriand.punkappchien.model.Beer;
import lombok.Getter;

public class BeerDetailsActivity extends AppCompatActivity {
	
	private TextView nameTextView;
	private TextView taglineTextView;
	private TextView descriptionTextView;
	private TextView abvTextView;
	private TextView ibuTextView;
	@Getter
	private ImageView beerImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beer_details);
		
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
		}
		
		// Gather beer data from Intent
		Beer beer = (Beer) getIntent().getSerializableExtra("beer");
		
		if (beer == null) {
			finish();
			return;  // Useless but avoids IDE complaints
		}
		
		// Gather references to activity views
		nameTextView = findViewById(R.id.beer_details_name);
		taglineTextView = findViewById(R.id.beer_details_tagline);
		descriptionTextView = findViewById(R.id.beer_details_description);
		abvTextView = findViewById(R.id.beer_details_abv);
		ibuTextView = findViewById(R.id.beer_details_ibu);
		beerImageView = findViewById(R.id.beer_details_image);
		
		new GetBeerImageForDetails(this).execute(beer);
		nameTextView.setText(beer.getName());
		taglineTextView.setText(beer.getTagline());
		descriptionTextView.setText(beer.getDescription());
		abvTextView.setText(String.format(Locale.getDefault(), "%.2f", beer.getAbv()));
		ibuTextView.setText(String.valueOf(beer.getIbu()));
	}
	
	
	/**
	 * Handles the back arrow in the action bar
	 * @param item ???
	 * @return ???
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}
}
