package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieData implements Parcelable {

    private final static String Id_Tag = "id";
    private final static String Poster_Path_Tag = "poster_path";
    private final static String Original_Name_Tag = "original_title";
    private final static String Overview_Tag = "overview";
    private final static String User_Rating_Tag = "vote_average";
    private final static String Release_Date_Tag = "release_date";

    @SerializedName(Id_Tag)
    private Integer movieId;

    @SerializedName(Poster_Path_Tag)
    private String moviePosterPath;

    @SerializedName(Original_Name_Tag)
    private String movieOriginalName;

    @SerializedName(Overview_Tag)
    private String movieOverview;

    @SerializedName(Release_Date_Tag)
    private String movieReleaseDate;

    @SerializedName(User_Rating_Tag)
    private Double movieUserRating;

    public MovieData() { }

    public MovieData(Integer movieId, String moviePosterPath, String movieOriginalName, String movieOverview, String movieReleaseDate, Double movieUserRating) {
        this.movieId = movieId;
        this.moviePosterPath = moviePosterPath;
        this.movieOriginalName = movieOriginalName;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieUserRating = movieUserRating;
    }

    private MovieData(Parcel in) {
        this.movieId = in.readInt();
        this.moviePosterPath = in.readString();
        this.movieOriginalName = in.readString();
        this.movieOverview = in.readString();
        this.movieReleaseDate = in.readString();
        this.movieUserRating = in.readDouble();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(moviePosterPath);
        dest.writeString(movieOriginalName);
        dest.writeString(movieOverview);
        dest.writeString(movieReleaseDate);
        dest.writeDouble(movieUserRating);
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {

        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
