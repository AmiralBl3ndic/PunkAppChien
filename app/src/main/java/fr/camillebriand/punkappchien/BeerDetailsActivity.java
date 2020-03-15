package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import fr.camillebriand.punkappchien.async.GetBeerImageForDetails;
import fr.camillebriand.punkappchien.async.UpdateBeerInDatabaseTask;
import fr.camillebriand.punkappchien.model.Beer;
import fr.camillebriand.punkappchien.net.FetchPunkAPITask;
import lombok.Getter;

public class BeerDetailsActivity extends AppCompatActivity {
	
	private TextView nameTextView;
	private TextView taglineTextView;
	private TextView descriptionTextView;
	private TextView abvTextView;
	private TextView ibuTextView;
	private TextView beerGradeTextView;
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
		final Beer beer = (Beer) getIntent().getSerializableExtra("beer");
		
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
		beerGradeTextView = findViewById(R.id.beer_rate_textview);
		
		// Handle clicks on the "Share this beer" button
		findViewById(R.id.details_share_beer_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String shareMessageContent = "You should try this beer!\n" +
						beer.getName() + "\"" + beer.getTagline() + "\""
						+"\n\nhttps://play.google.com/store/apps/details?id=fr.camillebriand.punkappchien";
				
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(Intent.EXTRA_TEXT, shareMessageContent);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});
		
		// Handle SeekBar changes only if API version is sufficient
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			((SeekBar) findViewById(R.id.rating_seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					beer.setGrade(progress);
					beerGradeTextView.setText(String.valueOf(progress));
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {}
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					new UpdateBeerInDatabaseTask(getApplicationContext()).execute(beer);
				}
			});
		}
		
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
