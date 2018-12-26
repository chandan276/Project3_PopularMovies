package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewsResponse {

    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<MovieReviewsData> results;

    public MovieReviewsResponse(List<MovieReviewsData> results) {
        this.results = results;
    }

    public List<MovieReviewsData> getResults() {
        return results;
    }

    public void setResults(List<MovieReviewsData> results) {
        this.results = results;
    }
}
