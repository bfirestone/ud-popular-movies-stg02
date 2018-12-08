package com.bfirestone.udacity.popularmovies.database.entity;

import android.arch.persistence.room.Entity;

import java.util.List;

import static com.bfirestone.udacity.popularmovies.AppConstants.DB_TABLE_FAVES;

@Entity(tableName = DB_TABLE_FAVES)
public class FaveMovieEntity extends MovieEntity {

    public FaveMovieEntity(String posterPath, boolean adult, String overview, String releaseDate,
                           List<Integer> genreIds, int id, String originalTitle, String originalLanguage,
                           String title, String backdropPath, double popularity, int voteCount, boolean video,
                           double voteAverage) {

        super(posterPath, adult, overview, releaseDate, genreIds, id, originalTitle, originalLanguage, title, backdropPath, popularity, voteCount, video, voteAverage);
    }
}
