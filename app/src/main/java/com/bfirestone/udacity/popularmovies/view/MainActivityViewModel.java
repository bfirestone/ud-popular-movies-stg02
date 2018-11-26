package com.bfirestone.udacity.popularmovies.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.database.repo.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MainActivityViewModel.class.getSimpleName();
    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<MovieEntity>> getFavedMovies(String orderByColumn) {
        Log.d(LOG_TAG, "fetching faved movies from database via ViewModel sorted_by=" + orderByColumn);

        String sortByRating = getApplication().getResources().getString(
                R.string.pref_fave_rating_value);
        String sortByPopularity = getApplication().getResources().getString(
                R.string.pref_fave_popularity_value);

        if (orderByColumn.equals(sortByRating)) {
            return movieRepository.loadAllMovieFavesByRating();
        }

        if (orderByColumn.equals(sortByPopularity)) {
            return movieRepository.loadAllMovieFavesByPopularity();
        }

        return movieRepository.loadAllMovieFavesByTitle();
    }
}
