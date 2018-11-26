package com.bfirestone.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.Utils.MovieApiClient;
import com.bfirestone.udacity.popularmovies.Utils.NetworkConnectionDetector;
import com.bfirestone.udacity.popularmovies.adapters.MovieListAdapter;
import com.bfirestone.udacity.popularmovies.api.models.GenreParcelableSparseArray;
import com.bfirestone.udacity.popularmovies.api.models.MovieListResponse;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.service.MovieDatabaseApiService;
import com.bfirestone.udacity.popularmovies.view.MainActivityViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bfirestone.udacity.popularmovies.AppConstants.MOVIE_ENTITY_LIST;
import static com.bfirestone.udacity.popularmovies.AppConstants.SAVED_SORT_TYPE;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @BindString(R.string.TMDB_API_KEY)
    String apiKey;

    private MovieSortType movieSortType;
    private MovieDatabaseApiService movieDatabaseApiService;
    private MovieListAdapter mMovieListAdapter;
    private String faveSortBy;
    public GenreParcelableSparseArray genreParcelableSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSharedPreferences();

        // Initialize the adapter and attach it to the RecyclerView
        mMovieListAdapter = new MovieListAdapter(this, this);

        // Setup recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieListAdapter);

        genreParcelableSparseArray = new GenreParcelableSparseArray();

        movieDatabaseApiService = new MovieApiClient()
                .getRetrofitClient(getResources().getString(R.string.TMDB_BASE_API_URL))
                .create(MovieDatabaseApiService.class);

        getMovieListBySort(MovieSortType.MOST_POPULAR);
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadFaveSortFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void getMovieListBySort(MovieSortType movieSortType) {
        this.movieSortType = movieSortType;

        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        if (detector.isNetworkAvailable(this)) {

            Call<MovieListResponse> call;

            if (movieSortType == MovieSortType.FAVORITES) {
                setTitle(R.string.menu_display_faves);
                viewModel.getFavedMovies(faveSortBy).observe(this, favorites -> {
                    if (favorites != null) {
                        mMovieListAdapter.clear();
                        mMovieListAdapter.setMovieList(favorites);

                        if (mMovieListAdapter.getMovieEntityList().size() == 0) {
                            Toast.makeText(getApplicationContext(), R.string.FavesNotFound,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                if (movieSortType == MovieSortType.HIGHEST_RATING) {
                    setTitle(R.string.menu_highest_rating);
                    call = movieDatabaseApiService.getTopRatedMovies(apiKey, 1);
                } else {
                    setTitle(R.string.menu_most_popular);
                    call = movieDatabaseApiService.getPopularMovies(apiKey, 1);
                }

                Log.i(LOG_TAG, "movie db api: " + call.request().url());

                call.enqueue(new Callback<MovieListResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieListResponse> call,
                                           @NonNull Response<MovieListResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            List<MovieEntity> movieDetails = response.body().getResults();
                            Log.i(LOG_TAG, "[movie_details] " + movieDetails);
                            mMovieListAdapter.clear();
                            mMovieListAdapter.setMovieList(movieDetails);
                            Log.d(LOG_TAG, "[top_rated_movies] num fetched=" + movieDetails.size());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieListResponse> call,
                                          @NonNull Throwable t) {

                        Toast.makeText(
                                MainActivity.this, "Error Fetching MovieEntity List",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            Log.i(LOG_TAG, "[network] not available");
        }
    }

    private void loadFaveSortFromPreferences(SharedPreferences sharedPreferences) {
        faveSortBy = sharedPreferences.getString(getString(R.string.pref_fave_key),
                getString(R.string.pref_fave_default_sort));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // based on id present user with options to sort by popularity or highest rated
        if (id == R.id.sort_highest_rating) {
            getMovieListBySort(MovieSortType.HIGHEST_RATING);
        } else if (id == R.id.sort_most_popular) {
            getMovieListBySort(MovieSortType.MOST_POPULAR);
        } else if (id == R.id.sort_display_faves) {
            getMovieListBySort(MovieSortType.FAVORITES);
        } else if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_SORT_TYPE, movieSortType.getValue());
        outState.putParcelableArrayList(MOVIE_ENTITY_LIST, mMovieListAdapter.getMovieEntityList());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Log.i(LOG_TAG, "[SelectedMovie] " + clickedItemIndex);
        intent.putExtra("MovieEntity", mMovieListAdapter.getMovieEntityList().get(clickedItemIndex));
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_fave_key))) {
            faveSortBy = sharedPreferences.getString(key, getResources().getString(R.string.pref_fave_default_sort));
        }
    }
}
