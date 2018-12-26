package com.example.android.popularmovies.rest;

import com.example.android.popularmovies.model.MovieResponse;
import com.example.android.popularmovies.model.MovieReviewsResponse;
import com.example.android.popularmovies.model.MovieVideosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    String PARAM_KEY = "api_key";
    String MOVIE_ID_KEY = "movie_id";

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query(PARAM_KEY) String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query(PARAM_KEY) String apiKey);

    @GET("/3/movie/{movie_id}/videos")
    Call<MovieVideosResponse> getMovieVideos(@Path(MOVIE_ID_KEY) int movieId, @Query(PARAM_KEY) String apiKey);

    @GET("/3/movie/{movie_id}/reviews")
    Call<MovieReviewsResponse> getMovieReviews(@Path(MOVIE_ID_KEY) int movieId, @Query(PARAM_KEY) String apiKey);
}
