package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class MovieDetailViewModel extends ViewModel {

    private LiveData<FavoriteMovies> favoriteMovie;

    public MovieDetailViewModel(AppDatabase database, int movieId) {
        favoriteMovie = database.favoriteDao().loadFavoriteById(movieId);
    }

    public LiveData<FavoriteMovies> getTask() {
        return favoriteMovie;
    }
}
