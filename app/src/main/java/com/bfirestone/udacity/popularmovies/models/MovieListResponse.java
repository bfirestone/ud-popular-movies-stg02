package com.bfirestone.udacity.popularmovies.models;

import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.squareup.moshi.Json;

import java.util.List;

public class MovieListResponse {

    @Json(name ="page")
    private int page;

    @Json(name ="results")
    private List<MovieEntity> results;

    @Json(name ="total_results")
    private int totalResults;

    @Json(name ="total_pages")
    private int totalPages;

    public MovieListResponse(int page, List<MovieEntity> results, int totalResults, int totalPages) {

        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public List<MovieEntity> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "MovieListResponse{" +
                "page=" + page +
                ", results=" + results +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                '}';
    }
}

