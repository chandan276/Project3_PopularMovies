package com.example.android.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteMovies {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String moviePosterPath;
    private String movieOriginalName;
    private String movieOverview;
    private String movieReleaseDate;
    private Double movieUserRating;

    public FavoriteMovies(int id, String moviePosterPath, String movieOriginalName, String movieOverview, String movieReleaseDate, Double movieUserRating) {
        this.id = id;
        this.moviePosterPath = moviePosterPath;
        this.movieOriginalName = movieOriginalName;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieUserRating = movieUserRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieOriginalName() {
        return movieOriginalName;
    }

    public void setMovieOriginalName(String movieOriginalName) {
        this.movieOriginalName = movieOriginalName;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public Double getMovieUserRating() {
        return movieUserRating;
    }

    public void setMovieUserRating(Double movieUserRating) {
        this.movieUserRating = movieUserRating;
    }
}
