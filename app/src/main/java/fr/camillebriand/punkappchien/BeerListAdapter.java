package fr.camillebriand.punkappchien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.camillebriand.punkappchien.async.GetBeerImageForListView;
import fr.camillebriand.punkappchien.model.Beer;
import lombok.Getter;

public class BeerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Beer> beerList;
    private LayoutInflater inflater;
    
    @Getter
    private ImageView beerImageView;

    public BeerListAdapter(Context context, ArrayList<Beer> beerList) {
        this.context = context;
        this.beerList = beerList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beerList.size();
    }

    @Override
    public Beer getItem(int position) {
        return beerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "ResourceType"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_beer, null);

        final Beer currentBeer =  getItem(position);
        
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(context, BeerDetailsActivity.class);
                detailsIntent.putExtra("beer", currentBeer);
                context.startActivity(detailsIntent);
            }
        });
        
        this.beerImageView = convertView.findViewById(R.id.item_icon);
        
        TextView beerNameView = convertView.findViewById(R.id.beer_name);
        beerNameView.setText(currentBeer.getName());
    
        new GetBeerImageForListView(this).execute(currentBeer);
        
        return convertView;
    }
}
