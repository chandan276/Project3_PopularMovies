package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieReviewsData implements Parcelable {

    private final static String Id_Tag = "id";
    private final static String Author_Tag = "author";
    private final static String Content_Tag = "content";
    private final static String Url_Tag = "url";

    @SerializedName(Id_Tag)
    private String movieId;

    @SerializedName(Author_Tag)
    private String movieReviewAuthor;

    @SerializedName(Content_Tag)
    private String movieReviewContent;

    @SerializedName(Url_Tag)
    private String movieContentUrl;

    public MovieReviewsData() { }

    public MovieReviewsData(String movieId, String movieReviewAuthor, String movieReviewContent, String movieContentUrl) {
        this.movieId = movieId;
        this.movieReviewAuthor = movieReviewAuthor;
        this.movieReviewContent = movieReviewContent;
        this.movieContentUrl = movieContentUrl;
    }

    private MovieReviewsData(Parcel in) {
        this.movieId = in.readString();
        this.movieReviewAuthor = in.readString();
        this.movieReviewContent = in.readString();
        this.movieContentUrl = in.readString();
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieReviewAuthor() {
        return movieReviewAuthor;
    }

    public void setMovieReviewAuthor(String movieReviewAuthor) {
        this.movieReviewAuthor = movieReviewAuthor;
    }

    public String getMovieReviewContent() {
        return movieReviewContent;
    }

    public void setMovieReviewContent(String movieReviewContent) {
        this.movieReviewContent = movieReviewContent;
    }

    public String getMovieContentUrl() {
        return movieContentUrl;
    }

    public void setMovieContentUrl(String movieContentUrl) {
        this.movieContentUrl = movieContentUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(movieReviewAuthor);
        parcel.writeString(movieReviewContent);
        parcel.writeString(movieContentUrl);
    }

    public static final Parcelable.Creator<MovieReviewsData> CREATOR = new Parcelable.Creator<MovieReviewsData>() {

        public MovieReviewsData createFromParcel(Parcel in) {
            return new MovieReviewsData(in);
        }

        public MovieReviewsData[] newArray(int size) {
            return new MovieReviewsData[size];
        }
    };
}
