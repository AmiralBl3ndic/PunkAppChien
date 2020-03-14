package fr.camillebriand.punkappchien.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.camillebriand.punkappchien.model.Beer;

@Dao
public interface BeerDAO {
	@Query("SELECT * FROM Beers")
	List<Beer> getBeers();
	
	@Insert
	void insertBeer(Beer beer);
	
	@Update
	void updateBeer(Beer beer);
	
	@Delete
	void deleteBeer(Beer beer);
}
