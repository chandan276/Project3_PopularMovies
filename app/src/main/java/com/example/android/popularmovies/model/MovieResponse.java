package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<MovieData> results;

    public MovieResponse(List<MovieData> results) {
        this.results = results;
    }

    public List<MovieData> getResults() {
        return results;
    }

    public void setResults(List<MovieData> results) {
        this.results = results;
    }
}
