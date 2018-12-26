package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.model.MovieResponse;
import com.example.android.popularmovies.model.MovieReviewsResponse;
import com.example.android.popularmovies.model.MovieVideosResponse;
import com.example.android.popularmovies.rest.MovieApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = BuildConfig.API_KEY;

    final public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    final public static String IMAGE_SIZE = "w185";

    final public static String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com/watch?v=";

    private static Retrofit retrofit = null;

    public static void connectAndGetMovieData(int sortOrder, final Callback<MovieResponse> arrayCallback) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieResponse> call;
        if (sortOrder == 0) {
            call = movieApiService.getPopularMovies(API_KEY);
        } else {
            call = movieApiService.getTopRatedMovies(API_KEY);
        }

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }

    public static void getMovieVideosData(int movieId, final Callback<MovieVideosResponse> responseCallback) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieVideosResponse> call = movieApiService.getMovieVideos(movieId, API_KEY);

        call.enqueue(new Callback<MovieVideosResponse>() {

            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                responseCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable throwable) {
                responseCallback.onFailure(call, throwable);
            }
        });
    }

    public static void getMovieReviewsData(int movieId, final Callback<MovieReviewsResponse> responseCallback) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieReviewsResponse> call = movieApiService.getMovieReviews(movieId, API_KEY);

        call.enqueue(new Callback<MovieReviewsResponse>() {

            @Override
            public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                responseCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<MovieReviewsResponse> call, Throwable throwable) {
                responseCallback.onFailure(call, throwable);
            }
        });
    }
}

