package com.bfirestone.udacity.popularmovies.models;


import com.squareup.moshi.Json;

import java.util.List;

public class GenreListResponse {

    @Json(name ="genres")
    private List<Genre> genres;

    public GenreListResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}