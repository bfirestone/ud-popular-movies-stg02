package com.bfirestone.udacity.popularmovies.models;


import com.squareup.moshi.Json;

public class Genre {

    @Json(name = "id")
    private int id;

    @Json(name ="name")
    private String name;

    public Genre(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GenreListResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}