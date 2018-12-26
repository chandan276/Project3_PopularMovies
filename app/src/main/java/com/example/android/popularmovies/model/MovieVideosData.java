package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieVideosData implements Parcelable {

    private final static String Id_Tag = "id";
    private final static String Key_Tag = "key";
    private final static String Name_Tag = "name";
    private final static String Site_Tag = "site";
    private final static String Size_Tag = "size";
    private final static String Type_Tag = "type";

    @SerializedName(Id_Tag)
    private String movieId;

    @SerializedName(Key_Tag)
    private String movieVideoKey;

    @SerializedName(Name_Tag)
    private String movieTrailerName;

    @SerializedName(Site_Tag)
    private String movieTrailerSite;

    @SerializedName(Size_Tag)
    private Integer movieTrailerSize;

    @SerializedName(Type_Tag)
    private String movieVideoType;

    public MovieVideosData() { }

    public MovieVideosData(String movieId, String movieVideoKey, String movieTrailerName, String movieTrailerSite, Integer movieTrailerSize, String movieVideoType) {
        this.movieId = movieId;
        this.movieVideoKey = movieVideoKey;
        this.movieTrailerName = movieTrailerName;
        this.movieTrailerSite = movieTrailerSite;
        this.movieTrailerSize = movieTrailerSize;
        this.movieVideoType = movieVideoType;
    }

    private MovieVideosData(Parcel in) {
        this.movieId = in.readString();
        this.movieVideoKey = in.readString();
        this.movieTrailerName = in.readString();
        this.movieTrailerSite = in.readString();
        this.movieTrailerSize = in.readInt();
        this.movieVideoType = in.readString();
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieVideoKey() {
        return movieVideoKey;
    }

    public void setMovieVideoKey(String movieVideoKey) {
        this.movieVideoKey = movieVideoKey;
    }

    public String getMovieTrailerName() {
        return movieTrailerName;
    }

    public void setMovieTrailerName(String movieTrailerName) {
        this.movieTrailerName = movieTrailerName;
    }

    public String getMovieTrailerSite() {
        return movieTrailerSite;
    }

    public void setMovieTrailerSite(String movieTrailerSite) {
        this.movieTrailerSite = movieTrailerSite;
    }

    public Integer getMovieTrailerSize() {
        return movieTrailerSize;
    }

    public void setMovieTrailerSize(Integer movieTrailerSize) {
        this.movieTrailerSize = movieTrailerSize;
    }

    public String getMovieVideoType() {
        return movieVideoType;
    }

    public void setMovieVideoType(String movieVideoType) {
        this.movieVideoType = movieVideoType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(movieVideoKey);
        parcel.writeString(movieTrailerName);
        parcel.writeString(movieTrailerSite);
        parcel.writeInt(movieTrailerSize);
        parcel.writeString(movieVideoType);
    }

    public static final Parcelable.Creator<MovieVideosData> CREATOR = new Parcelable.Creator<MovieVideosData>() {

        public MovieVideosData createFromParcel(Parcel in) {
            return new MovieVideosData(in);
        }

        public MovieVideosData[] newArray(int size) {
            return new MovieVideosData[size];
        }
    };
}
