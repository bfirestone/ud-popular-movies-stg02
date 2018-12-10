package com.bfirestone.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.Utils.NetworkConnectionDetector;
import com.bfirestone.udacity.popularmovies.adapter.MovieListAdapter;
import com.bfirestone.udacity.popularmovies.api.MovieApiClient;
import com.bfirestone.udacity.popularmovies.api.model.MovieListResponse;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.listener.EndlessRecyclerViewScrollListener;
import com.bfirestone.udacity.popularmovies.listener.ItemClickListener;
import com.bfirestone.udacity.popularmovies.service.TheMovieDatabaseApiService;
import com.bfirestone.udacity.popularmovies.view.MainActivityViewModel;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bfirestone.udacity.popularmovies.Utils.DisplayUtils.calculateColumns;


public class MainActivity extends AppCompatActivity implements ItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @BindInt(R.integer.dimen_main_poster_width)
    int mDimensionPosterWidth;

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

    @BindString(R.string.state_movie_list_pos)
    String STATE_MOVIE_LIST_POS;

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

    @BindString(R.string.moviedb_api_sort_popularity)
    String TMDB_API_SORT_POPULARITY;

    @BindString(R.string.moviedb_api_sort_rating)
    String TMDB_API_SORT_RATING;

    private MainActivityViewModel viewModel;
    private MovieSortType movieSortType;
    private TheMovieDatabaseApiService movieDatabaseApiService;
    private MovieListAdapter mMovieListAdapter;
    private GridLayoutManager mGridLayoutManager;
    private List<MovieEntity> movieEntityList;
    private String faveSortBy;
    private String activityTitle;
    private RecyclerView.SmoothScroller smoothScroller;
    private static int totalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSharedPreferences();

        smoothScroller = new LinearSmoothScroller(this) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        // Initialize the recycler adapter
        mMovieListAdapter = new MovieListAdapter(this, this);

        // Setup recycler view
        mGridLayoutManager = new GridLayoutManager(this, calculateColumns(this,
                mDimensionPosterWidth));

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mMovieListAdapter);

        if (savedInstanceState != null) {
            mRecyclerView.post(() -> {
                int pos = savedInstanceState.getInt(STATE_MOVIE_LIST_POS);

                if (pos == -1) {
                    pos = 0;
                }

                Log.v(LOG_TAG, "SETTING_POS: " + pos);
                smoothScroller.setTargetPosition(pos);
                mGridLayoutManager.startSmoothScroll(smoothScroller);
            });
        }

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages && movieSortType != MovieSortType.FAVORITES) {
                    getMovieListBySort(page + 1);
                }
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mSwipeContainer.setOnRefreshListener(() -> getMovieListBySort(1));
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // setup retrofit cache
        int cacheSize = 40 * 1024 * 1024; // 40 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);

        // setup retrofit client
        movieDatabaseApiService = new MovieApiClient()
                .getRetrofitClient(TMDB_BASE_API_URL, new OkHttpClient.Builder()
                        .cache(cache)
                        .build())
                .create(TheMovieDatabaseApiService.class);

        if (savedInstanceState == null) {
            movieSortType  = MovieSortType.MOST_POPULAR;
            getMovieListBySort(1);
        }
    }

    private void getMovieListBySort(final int requestPage) {
        Log.v(LOG_TAG, "method=getMovieListBySort() sort_type=" + MovieSortType.getSortType(movieSortType.getSortValue()));
        // update title
        updateTitle();

        if (movieSortType == MovieSortType.FAVORITES) {
            viewModel.getFavedMovies(faveSortBy).observe(this, favorites -> {
                if (favorites != null) {
                    totalPages = favorites.size();
                    movieEntityList = favorites;
                    mMovieListAdapter.setMovieList(favorites);

                    if (mMovieListAdapter.getMovieEntityList().size() == 0) {
                        Toast.makeText(getApplicationContext(), R.string.no_faves_found,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            NetworkConnectionDetector detector = new NetworkConnectionDetector();

            if (detector.isNetworkAvailable(this)) {
                String apiSortString;

                switch (movieSortType) {
                    case MOST_POPULAR:
                        apiSortString = TMDB_API_SORT_POPULARITY;
                        break;
                    case HIGHEST_RATING:
                        apiSortString = TMDB_API_SORT_RATING;
                        break;
                    default:
                        apiSortString = TMDB_API_SORT_RATING;
                        break;
                }

                Call<MovieListResponse> call = movieDatabaseApiService.getMoviesBySortOrder(
                        apiSortString, TMDB_API_KEY, requestPage);

                Log.v(LOG_TAG, String.format("tmdb api_url=[%s]", call.request().url()));

                call.enqueue(new Callback<MovieListResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieListResponse> call,
                                           @NonNull Response<MovieListResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            totalPages = response.body().getTotalPages();

                            if (requestPage == 1) {
                                movieEntityList = response.body().getResults();
                                mMovieListAdapter.setMovieList(movieEntityList);
                                Log.v(LOG_TAG, "[top_rated_movies] num fetched=" + movieEntityList.size());
                            } else {
                                List<MovieEntity> movieEntities = response.body().getResults();
                                movieEntities.forEach(movieEntity -> {
                                    movieEntityList.add(movieEntity);
                                    mMovieListAdapter.addMovie(movieEntity);
                                });
                            }
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


            } else {
                Log.e(LOG_TAG, "[network] not available");
            }
        }

        mSwipeContainer.setRefreshing(false);
    }

    public void updateTitle() {
        switch (movieSortType) {
            case HIGHEST_RATING:
                activityTitle = getString(R.string.menu_highest_rating);
                break;
            case MOST_POPULAR:
                activityTitle = getString(R.string.menu_most_popular);
                break;
            case FAVORITES:
                activityTitle = getString(R.string.menu_display_faves);
                break;
        }

        setTitle(activityTitle);
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

        switch (id) {
            case R.id.sort_by_rating:
                movieSortType = MovieSortType.HIGHEST_RATING;
                break;
            case R.id.sort_by_popularity:
                movieSortType = MovieSortType.MOST_POPULAR;
                break;
            case R.id.sort_display_faves:
                movieSortType = MovieSortType.FAVORITES;
                break;
        }

        totalPages = 0;
        getMovieListBySort(1);

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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREF_FAVE_KEY)) {
            faveSortBy = sharedPreferences.getString(key, PREF_FAVE_DEFAULT_SORT);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(LOG_TAG, "instance state=restored");
        movieSortType = MovieSortType.getSortType(savedInstanceState.getInt(STATE_SORT_TYPE));
        movieEntityList = savedInstanceState.getParcelableArrayList(STATE_MOVIE_ENTITY);
        mMovieListAdapter.setMovieList(movieEntityList);
        updateTitle();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(LOG_TAG, "instance state=saved");
        outState.putInt(STATE_MOVIE_LIST_POS, mGridLayoutManager.findFirstCompletelyVisibleItemPosition());
        outState.putInt(STATE_SORT_TYPE, movieSortType.getSortValue());
        outState.putParcelableArrayList(STATE_MOVIE_ENTITY, mMovieListAdapter.getMovieEntityList());
        super.onSaveInstanceState(outState);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (mRecyclerView.getLayoutManager() != null) {
//            smoothScroller.setTargetPosition(currentVisiblePosition);
//            currentVisiblePosition = 0;
//        }
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (mRecyclerView.getLayoutManager() != null) {
//            currentVisiblePosition = ((GridLayoutManager) mRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }
//
//        Log.v(LOG_TAG, String.format("instance state=pause position=%d", currentVisiblePosition));
//    }

    @Override
    public void onRefresh() {

    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadFaveSortFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    private void loadFaveSortFromPreferences(SharedPreferences sharedPreferences) {
        faveSortBy = sharedPreferences.getString(PREF_FAVE_KEY, PREF_FAVE_DEFAULT_SORT);
    }

    // TODO: add dialog fragment for sort options/menu
//    private void showSortByMenu() {
//        DialogFragment sortingDialogFragment = new SortingDialogFragment();
//        sortingDialogFragment.show(getFragmentManager(), SortingDialogFragment.TAG);
//    }
}
