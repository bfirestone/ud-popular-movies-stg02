package com.bfirestone.udacity.popularmovies.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.database.repo.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<MovieEntity>> getFavedMovies(String orderByColumn) {
        Log.d(TAG, "fetching faved movies from database via ViewModel sorted_by=" + orderByColumn);

        // pref_fave_rating_value
//        switch (orderByColumn) {
//            case R.string.menu_display_faves:
//        }
        return movieRepository.loadAllMovieFavesByTitle();
    }
}
