package com.bfirestone.udacity.popularmovies.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.bfirestone.udacity.popularmovies.AppConstants.DB_TABLE_GENRES;

@Entity(tableName = DB_TABLE_GENRES)
public class GenreEntity {

    @PrimaryKey
    private int genreId;
    private String name;

    public GenreEntity(int genreId, String name) {

        this.genreId = genreId;
        this.name = name;
    }

    public int getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }
}