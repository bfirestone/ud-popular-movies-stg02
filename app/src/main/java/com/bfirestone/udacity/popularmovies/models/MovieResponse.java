package com.bfirestone.udacity.popularmovies.models;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieResponse {

    @Json(name ="page")
    private int page;

    @Json(name ="results")
    private List<MovieDetails> results;

    @Json(name ="total_results")
    private int totalResults;

    @Json(name ="total_pages")
    private int totalPages;

    public MovieResponse(int page, List<MovieDetails> results, int totalResults, int totalPages) {

        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public List<MovieDetails> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}

