package com.example.android.popularmovies.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.adapter.MoviesAdapter;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.FavoriteMovies;
import com.example.android.popularmovies.database.MainViewModel;
import com.example.android.popularmovies.model.MovieData;
import com.example.android.popularmovies.model.MovieResponse;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.ProgressIndicatorHandler;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieItemClickListener {

    private static final int GRID_SPAN = 2;

    private static Context appContext;

    private RecyclerView mMovieList;

    private int selectedMenuOption = 0;
    private List<MovieData> movieDataList = new ArrayList<>();

    private static final String RESPONSE_CALLBACKS_TEXT_KEY = "callbacks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = this;

        //Set home screen title
        setPageTitle();

        mMovieList = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_SPAN);
        mMovieList.setLayoutManager(layoutManager);

        mMovieList.setHasFixedSize(true);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RESPONSE_CALLBACKS_TEXT_KEY)) {
                movieDataList.clear();
                movieDataList = savedInstanceState.getParcelableArrayList(RESPONSE_CALLBACKS_TEXT_KEY);
                upadteAdapter();
            } else {
                makeMovieListRequest();
            }
        } else {
            makeMovieListRequest();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RESPONSE_CALLBACKS_TEXT_KEY, new ArrayList<MovieData>(movieDataList));
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void setPageTitle() {

        String homeTitle = getString(R.string.home_screen_title);
        String postFix = "";

        switch (selectedMenuOption) {
            case 0:
                postFix = getString(R.string.menu_option_most_popular);
                break;

            case 1:
                postFix = getString(R.string.menu_option_top_rated);
                break;

            default:
                postFix = getString(R.string.menu_option_my_favorite);
                break;

        }

        setTitle(homeTitle + " - " + postFix);
    }

    private void upadteAdapter() {
        MoviesAdapter mAdapter = new MoviesAdapter(movieDataList.size(), this, movieDataList);
        mMovieList.setAdapter(mAdapter);
    }

    private void makeMovieListRequest() {
        ProgressIndicatorHandler.showProgressIndicator(MainActivity.getAppContext(), getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.connectAndGetMovieData(selectedMenuOption, new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    if (movieDataList.size() > 0) {
                        movieDataList.clear();
                    }
                    movieDataList = response.body().getResults();
                    upadteAdapter();
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                ProgressIndicatorHandler.hideProgressIndicator();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                ProgressIndicatorHandler.hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void getMyFavoriteMoviesList() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<FavoriteMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovies> favoriteMovies) {
                if (favoriteMovies != null) {
                    movieDataList.clear();
                    for (FavoriteMovies movie : favoriteMovies) {
                        MovieData movieData = new MovieData(movie.getId(), movie.getMoviePosterPath(), movie.getMovieOriginalName(),
                                movie.getMovieOverview(), movie.getMovieReleaseDate(), movie.getMovieUserRating());
                        movieDataList.add(movieData);
                    }
                    upadteAdapter();
                }
            }
        });
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Context context = MainActivity.this;
        Class destinationActivity = MovieDetailActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        MovieData movieModel = movieDataList.get(clickedItemIndex);
        intent.putExtra(Intent.EXTRA_TEXT, movieModel);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_most_popular) {
            selectedMenuOption = 0;
            makeMovieListRequest();
        } else if (itemThatWasClickedId == R.id.action_top_rated) {
            selectedMenuOption = 1;
            makeMovieListRequest();
        } else if (itemThatWasClickedId == R.id.action_my_favorite) {
            selectedMenuOption = 2;
            getMyFavoriteMoviesList();
        }

        setPageTitle();

        return true;
    }
}
