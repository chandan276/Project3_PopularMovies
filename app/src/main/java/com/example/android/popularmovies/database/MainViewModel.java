package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMovies>> favoriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        favoriteMovies = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteMovies>> getTasks() {
        return favoriteMovies;
    }
}
