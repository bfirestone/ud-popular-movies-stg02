package com.bfirestone.udacity.popularmovies.database.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.bfirestone.udacity.popularmovies.database.entity.GenreEntity;

import java.util.List;

@Dao
public interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveGenre(GenreEntity genreEntity);

    @Query("SELECT * FROM genres WHERE genreId IN (:genreIds)")
    LiveData<List<GenreEntity>> getGenresByIds(List<Integer> genreIds);

    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    LiveData<GenreEntity> getGenresById(int genreId);

    @Query("SELECT * FROM genres")
    LiveData<List<GenreEntity>> getAllGenres();
}
