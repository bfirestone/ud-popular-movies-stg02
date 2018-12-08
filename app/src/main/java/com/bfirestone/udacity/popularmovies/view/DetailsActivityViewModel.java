package com.bfirestone.udacity.popularmovies.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.database.repo.MovieRepository;

public class DetailsActivityViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String LOG_TAG = DetailsActivityViewModel.class.getSimpleName();

    private MovieRepository movieRepository;

    public DetailsActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public boolean isFave(int movieId) {
        return movieRepository.isFavorite(movieId);
    }

    public void addFave(MovieEntity movieEntity) {
        movieRepository.addMovieFave(movieEntity);
    }

    public void removeFave(MovieEntity movieEntity) {
        movieRepository.deleteMovieFave(movieEntity);
    }
}
