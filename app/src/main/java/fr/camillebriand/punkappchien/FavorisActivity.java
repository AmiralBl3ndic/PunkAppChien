package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import fr.camillebriand.punkappchien.model.Beer;

public class FavorisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
    
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ListView favorisListView = findViewById(R.id.fav_list_view);
        favorisListView.setAdapter(new BeerListAdapter(this, Beer.getFavourites()));
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
