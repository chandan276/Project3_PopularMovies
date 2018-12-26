package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteMovies>> loadAllFavorites();

    @Insert
    void insertTask(FavoriteMovies favoriteMovies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(FavoriteMovies favoriteMovies);

    @Delete
    void deleteTask(FavoriteMovies favoriteMovies);

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<FavoriteMovies> loadFavoriteById(int id);
}
