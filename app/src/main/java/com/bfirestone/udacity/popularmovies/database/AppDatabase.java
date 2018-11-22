package com.bfirestone.udacity.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.bfirestone.udacity.popularmovies.database.converter.Converter;
import com.bfirestone.udacity.popularmovies.database.dao.GenreDao;
import com.bfirestone.udacity.popularmovies.database.dao.MovieDao;
import com.bfirestone.udacity.popularmovies.database.entity.FaveMovieEntity;
import com.bfirestone.udacity.popularmovies.database.entity.GenreEntity;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;

@Database(entities = {
        MovieEntity.class,
        GenreEntity.class,
        FaveMovieEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popular_movies";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
    public abstract GenreDao genreDao();
}