package com.bfirestone.udacity.popularmovies.database.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.bfirestone.udacity.popularmovies.database.entity.GenreEntity;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<MovieEntity> movieEntityList);

    @Query("SELECT * FROM movies ORDER BY movieId ASC")
    LiveData<List<MovieEntity>> loadMovies();

    @Query("DELETE FROM movies")
    void deleteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveGenres();

    @Query("SELECT * FROM genres WHERE genreId IN (:genreIds)")
    LiveData<List<GenreEntity>> getGenresByIds(List<Integer> genreIds);

    @Query("SELECT * FROM movies where movieId = :movieId")
    LiveData<MovieEntity> getMovieById(int movieId);
}
