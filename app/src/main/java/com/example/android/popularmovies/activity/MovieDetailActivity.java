package com.example.android.popularmovies.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.MovieDetailsAdapter;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.AppExecutors;
import com.example.android.popularmovies.database.FavoriteMovies;
import com.example.android.popularmovies.database.MovieDetailViewModel;
import com.example.android.popularmovies.database.MovieDetailViewModelFactory;
import com.example.android.popularmovies.model.MovieData;
import com.example.android.popularmovies.model.MovieReviewsData;
import com.example.android.popularmovies.model.MovieReviewsResponse;
import com.example.android.popularmovies.model.MovieVideosData;
import com.example.android.popularmovies.model.MovieVideosResponse;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.ProgressIndicatorHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailsAdapter.MovieTrailerClickListener {

    private TextView mOriginalNametv;
    private ImageView mPosterIv;
    private TextView mOverviewtv;
    private TextView mReleaseDatetv;
    private TextView mUserRatingtv;
    private TextView mDetailsHeader;
    private Menu menu;

    private static final String VIDEO_RESPONSE_CALLBACKS_TEXT_KEY = "videoCallbacks";
    private static final String REVIEW_RESPONSE_CALLBACKS_TEXT_KEY = "reviewCallbacks";

    private MovieData movieDetailData = null;
    private static final String TOTAL_RATING = "/10";

    private boolean isFavoriteSelected = false;

    private RecyclerView mMovieDetailsView;

    private List<MovieVideosData> movieVideosData = new ArrayList<>();
    private List<MovieReviewsData> movieReviewsData = new ArrayList<>();

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mOriginalNametv = (TextView) findViewById(R.id.movie_title);
        mPosterIv = (ImageView) findViewById(R.id.movie_image);
        mReleaseDatetv = (TextView) findViewById(R.id.movie_release_date);
        mUserRatingtv = (TextView) findViewById(R.id.movie_rating);
        mOverviewtv = (TextView) findViewById(R.id.movie_overview);
        mDetailsHeader = (TextView) findViewById(R.id.movie_details_header);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            movieDetailData = (MovieData) intent.getParcelableExtra(Intent.EXTRA_TEXT);
        }

        if (movieDetailData != null) {
            populateUI();
        }

        initializeRecyclerView();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(VIDEO_RESPONSE_CALLBACKS_TEXT_KEY)) {
                movieVideosData.clear();
                movieVideosData = savedInstanceState.getParcelableArrayList(VIDEO_RESPONSE_CALLBACKS_TEXT_KEY);
                upadteAdapter();
            }

            if (savedInstanceState.containsKey(REVIEW_RESPONSE_CALLBACKS_TEXT_KEY)) {
                movieReviewsData.clear();
                movieReviewsData = savedInstanceState.getParcelableArrayList(REVIEW_RESPONSE_CALLBACKS_TEXT_KEY);
                upadteAdapter();
            }
        } else {
            makeMovieTrailersRequest();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(VIDEO_RESPONSE_CALLBACKS_TEXT_KEY, new ArrayList<MovieVideosData>(movieVideosData));
        outState.putParcelableArrayList(REVIEW_RESPONSE_CALLBACKS_TEXT_KEY, new ArrayList<MovieReviewsData>(movieReviewsData));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.favorite_option, menu);
        loadFavoriteButton();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(this);
        } else if (id == R.id.action_my_favorite) {

            MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(mDb, movieDetailData.getMovieId());
            final MovieDetailViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);

            viewModel.getTask().observe(this, new Observer<FavoriteMovies>() {
                @Override
                public void onChanged(@Nullable final FavoriteMovies favoriteMovies) {
                    viewModel.getTask().removeObserver(this);
                    MenuItem menuItem = menu.findItem(R.id.action_my_favorite);

                    if (favoriteMovies == null) {

                        final FavoriteMovies favoriteMovie = new FavoriteMovies(movieDetailData.getMovieId(),
                                movieDetailData.getMoviePosterPath(), movieDetailData.getMovieOriginalName(),
                                movieDetailData.getMovieOverview(), movieDetailData.getMovieReleaseDate(),
                                movieDetailData.getMovieUserRating());

                        item.setIcon(R.drawable.button_pressed);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.favoriteDao().insertTask(favoriteMovie);
                            }
                        });
                        Toast.makeText(getApplicationContext(), getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
                    } else {
                        item.setIcon(R.drawable.button_normal);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.favoriteDao().deleteTask(favoriteMovies);
                            }
                        });
                        Toast.makeText(getApplicationContext(), getString(R.string.removed_from_favorite), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFavoriteButton() {
        MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(mDb, movieDetailData.getMovieId());
        final MovieDetailViewModel viewModel
                = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);

        viewModel.getTask().observe(this, new Observer<FavoriteMovies>() {
            @Override
            public void onChanged(@Nullable FavoriteMovies favoriteMovies) {
                viewModel.getTask().removeObserver(this);
                MenuItem menuItem = menu.findItem(R.id.action_my_favorite);
                if (favoriteMovies == null) {
                    menuItem.setIcon(R.drawable.button_normal);
                } else {
                    menuItem.setIcon(R.drawable.button_pressed);
                }
            }
        });
    }

    private void populateUI() {
        mDetailsHeader.setText(R.string.movie_details_header);

        if (movieDetailData.getMovieOriginalName() !=null) {
            mOriginalNametv.setText(movieDetailData.getMovieOriginalName());
        }

        if (movieDetailData.getMoviePosterPath() != null) {

            String imageUrlString = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SIZE + movieDetailData.getMoviePosterPath();

            Picasso.with(mPosterIv.getContext())
                    .load(imageUrlString)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mPosterIv);
        }

        if (movieDetailData.getMovieReleaseDate() != null) {
            String formattedReleaseDate = getString(R.string.movie_release_date_prefix) + " " + movieDetailData.getMovieReleaseDate();
            mReleaseDatetv.setText(formattedReleaseDate);
        }

        if (movieDetailData.getMovieUserRating().toString() != null) {
            String formattedUserRating = getString(R.string.movie_user_rating_prefix) + " " + movieDetailData.getMovieUserRating().toString() + TOTAL_RATING;
            mUserRatingtv.setText(formattedUserRating);
        }

        if (movieDetailData.getMovieOverview() != null) {
            mOverviewtv.setText(movieDetailData.getMovieOverview());
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initializeRecyclerView() {
        mMovieDetailsView = (RecyclerView) findViewById(R.id.recyclerview_details);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMovieDetailsView.setLayoutManager(layoutManager);

        mMovieDetailsView.setHasFixedSize(true);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mMovieDetailsView.getContext(),
                layoutManager.getOrientation());
        mMovieDetailsView.addItemDecoration(mDividerItemDecoration);
    }

    private void upadteAdapter() {
        MovieDetailsAdapter mAdapter = new MovieDetailsAdapter(movieVideosData, movieReviewsData, this);
        mMovieDetailsView.setAdapter(mAdapter);
    }

    private void makeMovieTrailersRequest() {
        ProgressIndicatorHandler.showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.getMovieVideosData(movieDetailData.getMovieId(), new Callback<MovieVideosResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieVideosResponse> call, @NonNull Response<MovieVideosResponse> response) {
                if (response.body() != null) {
                    movieVideosData = response.body().getResults();
                } else {
                    showToastMessage(getString(R.string.network_error));
                }
                ProgressIndicatorHandler.hideProgressIndicator();
                makeMovieReviewsRequest();
            }

            @Override
            public void onFailure(@NonNull Call<MovieVideosResponse> call, @NonNull Throwable t) {
                ProgressIndicatorHandler.hideProgressIndicator();
                showToastMessage(t.getLocalizedMessage());
                makeMovieReviewsRequest();
            }
        });
    }

    private void makeMovieReviewsRequest() {
        ProgressIndicatorHandler.showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.getMovieReviewsData(movieDetailData.getMovieId(), new Callback<MovieReviewsResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieReviewsResponse> call, @NonNull Response<MovieReviewsResponse> response) {
                if (response.body() != null) {
                    movieReviewsData = response.body().getResults();
                } else {
                    showToastMessage(getString(R.string.network_error));
                }
                ProgressIndicatorHandler.hideProgressIndicator();
                upadteAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewsResponse> call, @NonNull Throwable t) {
                ProgressIndicatorHandler.hideProgressIndicator();
                showToastMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onTrailerClick(int clickedItemIndex) {
        MovieVideosData videosData = movieVideosData.get(clickedItemIndex);
        Uri webpage = Uri.parse(NetworkUtils.YOUTUBE_VIDEO_BASE_URL + videosData.getMovieVideoKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
