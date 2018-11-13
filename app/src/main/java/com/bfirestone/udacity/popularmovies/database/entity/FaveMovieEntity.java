package com.bfirestone.udacity.popularmovies.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.bfirestone.udacity.popularmovies.AppConstants.DB_TABLE_FAVES;

@Entity(tableName = DB_TABLE_FAVES)
public class FaveMovieEntity {

    @PrimaryKey
    private int movieId;

    public FaveMovieEntity(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }
}
