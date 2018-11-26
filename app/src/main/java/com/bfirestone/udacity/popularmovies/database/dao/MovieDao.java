package com.bfirestone.udacity.popularmovies.database.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<MovieEntity> movieEntityList);

    @Query("SELECT * FROM movies ORDER BY id ASC")
    LiveData<List<MovieEntity>> loadMovies();

    @Query("DELETE FROM movies")
    void deleteMovies();

    @Query("SELECT * FROM movies where id = :movieId")
    MovieEntity getMovieById(int movieId);

    @Query("SELECT * FROM movies ORDER BY title ASC")
    LiveData<List<MovieEntity>> loadAllFavoriteMoviesOrderByTitle();

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    LiveData<List<MovieEntity>> loadAllFavoriteMoviesOrderByRating();

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    LiveData<List<MovieEntity>> loadAllFavoriteMoviesOrderByPopularity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(MovieEntity movieEntity);

    @Delete
    void deleteFavoriteMovie(MovieEntity movieEntity);
}
