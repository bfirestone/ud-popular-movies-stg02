package com.bfirestone.udacity.popularmovies.api.model;


import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@SuppressWarnings("unused")
public class GenreListResponse {

    @Json(name ="genres")
    private List<Genre> genres;

    public GenreListResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("genres", genres).toString();
    }
}