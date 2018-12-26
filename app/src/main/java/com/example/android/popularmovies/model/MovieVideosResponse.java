package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideosResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<MovieVideosData> results;

    public MovieVideosResponse(List<MovieVideosData> results) {
        this.results = results;
    }

    public List<MovieVideosData> getResults() {
        return results;
    }

    public void setResults(List<MovieVideosData> results) {
        this.results = results;
    }
}
