package fr.camillebriand.punkappchien.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.camillebriand.punkappchien.model.Beer;

@Database(entities = Beer.class, exportSchema = false, version = 1)
public abstract class BeerDatabase extends RoomDatabase {
	private static final String DB_NAME = "beer_db";
	private static BeerDatabase instance;
	
	public static synchronized BeerDatabase getInstance(Context context) {
		if (instance == null) {
			instance = Room.databaseBuilder(context.getApplicationContext(), BeerDatabase.class, DB_NAME)
				.fallbackToDestructiveMigration()
				.build();
		}
		return instance;
	}
	
	public abstract BeerDAO beerDAO();
}
