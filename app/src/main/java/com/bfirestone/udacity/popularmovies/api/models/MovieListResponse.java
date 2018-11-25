package com.bfirestone.udacity.popularmovies.api.models;

import android.support.annotation.NonNull;

import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@SuppressWarnings("unused")
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

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("page", page)
                .append("results", results)
                .append("totalResults", totalResults)
                .append("totalPages", totalPages).toString();
    }
}

