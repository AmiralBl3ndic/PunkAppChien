package fr.camillebriand.punkappchien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.camillebriand.punkappchien.model.Beer;

import static fr.camillebriand.punkappchien.model.Beer.dbBeers;

public class BeerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Beer> beerList;
    private LayoutInflater inflater;

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
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "ResourceType"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_item, null);

        Beer currentBeer =  getItem(position);
        String beerName = currentBeer.getName();


        TextView beerNameView = convertView.findViewById(R.id.item_name);
        beerNameView.setText(beerName);
        return convertView;
    }
}