package fr.camillebriand.punkappchien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import fr.camillebriand.punkappchien.model.Beer;

public class FavorisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        ListView favorisListView = findViewById(R.id.fav_list_view);
        favorisListView.setAdapter(new BeerListAdapter(this, Beer.getFavourites()));

        favorisListView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BeerDetailsActivity;
            }
        });
    }
}
