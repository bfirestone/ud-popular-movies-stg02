package com.bfirestone.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.Utils.NetworkConnectionDetector;
import com.bfirestone.udacity.popularmovies.adapters.ItemClickListener;
import com.bfirestone.udacity.popularmovies.adapters.MovieListAdapter;
import com.bfirestone.udacity.popularmovies.api.MovieApiClient;
import com.bfirestone.udacity.popularmovies.api.models.MovieListResponse;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.menu.SortingDialogFragment;
import com.bfirestone.udacity.popularmovies.service.TheMovieDatabaseApiService;
import com.bfirestone.udacity.popularmovies.view.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements ItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_container_main)
    SwipeRefreshLayout mSwipeContainer;

    @BindString(R.string.TMDB_API_KEY)
    String TMDB_API_KEY;

    @BindString(R.string.extra_name_entity)
    String EXTRA_MOVIE_ENTITY;

    @BindString(R.string.pref_fave_default_sort)
    String PREF_FAVE_DEFAULT_SORT;

    @BindString(R.string.pref_fave_key)
    String PREF_FAVE_KEY;

    @BindString(R.string.state_movie_entity_list)
    String STATE_MOVIE_ENTITY;

    @BindString(R.string.state_sort_type)
    String STATE_SORT_TYPE;

    @BindString(R.string.state_layout_manager)
    String STATE_LAYOUT_MANAGER;

    @BindString(R.string.state_activity_title)
    String STATE_ACTIVITY_TITLE;

    @BindString(R.string.TMDB_BASE_API_URL)
    String TMDB_BASE_API_URL;

    private MovieSortType movieSortType;
    private TheMovieDatabaseApiService movieDatabaseApiService;
    private MovieListAdapter mMovieListAdapter;
    private String faveSortBy;
    private String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSharedPreferences();

        // default menu sort type
        movieSortType = MovieSortType.MOST_POPULAR;

        // Initialize the adapter and attach it to the RecyclerView
        mMovieListAdapter = new MovieListAdapter(this, this);

        // Setup recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieListAdapter);

        mSwipeContainer.setOnRefreshListener(this::getMovieListBySort);
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        movieDatabaseApiService = new MovieApiClient()
                .getRetrofitClient(TMDB_BASE_API_URL)
                .create(TheMovieDatabaseApiService.class);

        if (savedInstanceState != null) {
            Log.v(LOG_TAG, "loading from saved instance");
            ArrayList<MovieEntity> movieList;

            if (savedInstanceState.containsKey(STATE_MOVIE_ENTITY)) {
                setTitle(savedInstanceState.getString(STATE_ACTIVITY_TITLE));
                movieList = savedInstanceState.getParcelableArrayList(STATE_MOVIE_ENTITY);
                mMovieListAdapter.clear();
                mMovieListAdapter.setMovieList(movieList);
            }

            if (savedInstanceState.containsKey(STATE_ACTIVITY_TITLE)) {
                activityTitle = savedInstanceState.getString(STATE_ACTIVITY_TITLE);
                setTitle(activityTitle);
            }
        }

        // if no saved state exists
        getMovieListBySort();
    }

    private void getMovieListBySort() {
        Log.v(LOG_TAG, "method=getMovieListBySort() sort_type=" + MovieSortType.get(movieSortType.getValue()));
        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getFavedMovies(faveSortBy).observe(this, favorites -> {
            if (favorites != null && movieSortType == MovieSortType.FAVORITES) {
                setTitle(R.string.menu_display_faves);
                mMovieListAdapter.clear();
                mMovieListAdapter.setMovieList(favorites);

                if (mMovieListAdapter.getMovieEntityList().size() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.FavesNotFound,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (detector.isNetworkAvailable(this)) {

            Call<MovieListResponse> call;

            if (movieSortType != MovieSortType.FAVORITES) {
                if (movieSortType == MovieSortType.HIGHEST_RATING) {
                    activityTitle = getString(R.string.menu_highest_rating);
                    call = movieDatabaseApiService.getTopRatedMovies(TMDB_API_KEY, 1);
                } else {
                    activityTitle = getString(R.string.menu_most_popular);
                    call = movieDatabaseApiService.getPopularMovies(TMDB_API_KEY, 1);
                }

                setTitle(activityTitle);

                Log.v(LOG_TAG, "movie db api: " + call.request().url());

                call.enqueue(new Callback<MovieListResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieListResponse> call,
                                           @NonNull Response<MovieListResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            List<MovieEntity> movieDetails = response.body().getResults();
                            Log.v(LOG_TAG, "[movie_details] " + movieDetails);
                            mMovieListAdapter.clear();
                            mMovieListAdapter.setMovieList(movieDetails);
                            Log.v(LOG_TAG, "[top_rated_movies] num fetched=" + movieDetails.size());
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
            Log.e(LOG_TAG, "[network] not available");
        }

        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        switch(id) {
            case R.id.sort_highest_rating:
                movieSortType = MovieSortType.HIGHEST_RATING;
                break;
            case R.id.sort_most_popular:
                movieSortType = MovieSortType.MOST_POPULAR;
                break;
            case R.id.sort_display_faves:
                movieSortType = MovieSortType.FAVORITES;
                break;
        }

        getMovieListBySort();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Log.v(LOG_TAG, "method=onMovieItemClick() [SelectedMovie] " + clickedItemIndex);
        intent.putExtra(EXTRA_MOVIE_ENTITY,
                mMovieListAdapter.getMovieEntityList().get(clickedItemIndex));

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOG_TAG, String.format(
                "method=onResume() sort_type=%s title=%s movie_count=%s movie_titles=%s",
                movieSortType, faveSortBy, mMovieListAdapter.getItemCount(), mMovieListAdapter.getMovieNameList()));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREF_FAVE_KEY)) {
            faveSortBy = sharedPreferences.getString(key, PREF_FAVE_DEFAULT_SORT);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "method=onRestoreInstanceState()");
        movieSortType = MovieSortType.get(savedInstanceState.getInt(STATE_SORT_TYPE));
        mMovieListAdapter.setMovieList(savedInstanceState.getParcelableArrayList(STATE_MOVIE_ENTITY));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(LOG_TAG, "method=onSaveInstanceState()");
        outState.putInt(STATE_SORT_TYPE, movieSortType.getValue());
        outState.putString(STATE_ACTIVITY_TITLE, activityTitle);
        outState.putParcelableArrayList(STATE_MOVIE_ENTITY, mMovieListAdapter.getMovieEntityList());
        if (mRecyclerView.getLayoutManager() != null) {
            outState.putParcelable(STATE_LAYOUT_MANAGER, mRecyclerView.
                    getLayoutManager().onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadFaveSortFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    private void loadFaveSortFromPreferences(SharedPreferences sharedPreferences) {
        faveSortBy = sharedPreferences.getString(PREF_FAVE_KEY, PREF_FAVE_DEFAULT_SORT);
    }

    @Override
    public void onRefresh() {

    }

//    private void showSortByMenu() {
//        DialogFragment sortingDialogFragment = new SortingDialogFragment();
//        sortingDialogFragment.show(getFragmentManager(), SortingDialogFragment.TAG);
//    }
}
