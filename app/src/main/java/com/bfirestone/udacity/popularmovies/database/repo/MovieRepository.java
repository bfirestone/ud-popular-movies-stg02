package com.bfirestone.udacity.popularmovies.database.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.bfirestone.udacity.popularmovies.Utils.AppExecutors;
import com.bfirestone.udacity.popularmovies.database.AppDatabase;
import com.bfirestone.udacity.popularmovies.database.dao.MovieDao;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;

import java.util.List;

public class MovieRepository {
    private MovieDao movieDao;
    private AppExecutors appExecutors;

    public MovieRepository(Application application) {
        movieDao = AppDatabase.getInstance(application).movieDao();
        appExecutors = AppExecutors.getExecutorInstance();
    }

    public LiveData<List<MovieEntity>> loadAllMovieFavesByTitle() {
        return movieDao.loadAllFavoriteMoviesOrderByTitle();
    }

    public LiveData<List<MovieEntity>> loadAllMovieFavesByPopularity() {
        return movieDao.loadAllFavoriteMoviesOrderByPopularity();
    }

    public LiveData<List<MovieEntity>> loadAllMovieFavesByRating() {
        return movieDao.loadAllFavoriteMoviesOrderByRating();
    }

    public boolean isFavorite(int movieId) {
        MovieEntity movieEntity = movieDao.getMovieById(movieId);
        return movieEntity != null;
    }

    public void addMovieFave(MovieEntity movieEntity) {
        appExecutors.getDiskIO().execute(() -> movieDao.insertFavoriteMovie(movieEntity));
    }

    public void deleteMovieFave(MovieEntity movieEntity) {
        appExecutors.getDiskIO().execute(() -> movieDao.deleteFavoriteMovie(movieEntity));
    }
}