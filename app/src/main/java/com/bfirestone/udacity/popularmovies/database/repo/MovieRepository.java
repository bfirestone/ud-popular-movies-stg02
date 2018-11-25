package com.bfirestone.udacity.popularmovies.database.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.Utils.AppExecutors;
import com.bfirestone.udacity.popularmovies.database.AppDatabase;
import com.bfirestone.udacity.popularmovies.database.dao.MovieDao;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;

import java.util.List;

public class MovieRepository {
    private MovieDao movieDao;
    private AppExecutors appExecutors;

    private LiveData<List<MovieEntity>> movieEntityList;

    public MovieRepository(Application application) {
        movieDao = AppDatabase.getInstance(application).movieDao();
        appExecutors = AppExecutors.getExecutorInstance();
    }

    public MovieRepository(Application application, int sortOrder) {
        //        movieEntityList = movieDao.loadAllFavoriteMovies();
//        movieEntityList = movieDao.loadAllFavoriteMoviesByTitle();

        switch (sortOrder) {
            case R.string.sort_display_faves:
                movieEntityList = movieDao.loadAllFavoriteMoviesByTitle();
                break;
        }
    }

    public LiveData<List<MovieEntity>> loadAllMovieFaves() {
        return movieDao.loadAllFavoriteMoviesByTitle();
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