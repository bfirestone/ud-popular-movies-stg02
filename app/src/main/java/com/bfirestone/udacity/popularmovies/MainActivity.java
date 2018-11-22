package com.bfirestone.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.Utils.MovieApiClient;
import com.bfirestone.udacity.popularmovies.Utils.NetworkConnectionDetector;
import com.bfirestone.udacity.popularmovies.adapters.MovieAdapter;
import com.bfirestone.udacity.popularmovies.database.AppDatabase;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.models.GenreParcelableSparseArray;
import com.bfirestone.udacity.popularmovies.models.MovieListResponse;
import com.bfirestone.udacity.popularmovies.service.MovieDatabaseService;
import com.bfirestone.udacity.popularmovies.view.MainActivityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bfirestone.udacity.popularmovies.AppConstants.SAVED_SORT_TYPE;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    private MainActivityViewModel viewModel;
    private static String apiKey;
    private MovieSortType movieSortType;
    private MovieDatabaseService movieDatabaseService;
    private MovieAdapter mAdapter;
    public GenreParcelableSparseArray genreParcelableSparseArray;
    private AppDatabase mAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        apiKey = getResources().getString(R.string.TMDB_API_KEY);

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new MovieAdapter(this, this);

        // Setup recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);

        mAppDatabase = AppDatabase.getInstance(getApplicationContext());
        genreParcelableSparseArray = new GenreParcelableSparseArray();

        movieDatabaseService = new MovieApiClient()
                .getRetrofitClient(getResources().getString(R.string.TMDB_BASE_API_URL))
                .create(MovieDatabaseService.class);

        getMovieListBySort(MovieSortType.MOST_POPULAR);
    }

    private void getMovieListBySort(MovieSortType movieSortType) {
        this.movieSortType = movieSortType;

        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        if (detector.isNetworkAvailable(this)) {

            Call<MovieListResponse> call;
            String movieServiceUrl;

            if (movieSortType == MovieSortType.FAVORITES) {
                setTitle(R.string.display_faves);
                viewModel.getFavedMovies().observe(this, favorites -> {
                    if (favorites != null) {
                        mAdapter.clear();
                        mAdapter.setMovieList(favorites);

                        if (mAdapter.getMovieEntityList().size() == 0) {
                            Toast.makeText(getApplicationContext(), R.string.FavesNotFound,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                if (movieSortType == MovieSortType.HIGHEST_RATING) {
                    setTitle(R.string.sort_highest_rating);
                    movieServiceUrl = movieDatabaseService.getTopRatedMovies(apiKey)
                            .request().url().toString();
                    call = movieDatabaseService.getTopRatedMovies(apiKey);
                } else {
                    setTitle(R.string.sort_most_popular);
                    movieServiceUrl = movieDatabaseService.getPopularMovies(apiKey)
                            .request().url().toString();
                    call = movieDatabaseService.getPopularMovies(apiKey);
                }

                Log.i(LOG_TAG, "movie db api: " + movieServiceUrl);

                call.enqueue(new Callback<MovieListResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieListResponse> call,
                                           @NonNull Response<MovieListResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            List<MovieEntity> movieDetails = response.body().getResults();
                            Log.i(LOG_TAG, "[movie_details] " + movieDetails);
                            mAdapter.clear();
                            mAdapter.setMovieList(movieDetails);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
        } else if (id == R.id.display_faves) {
            getMovieListBySort(MovieSortType.FAVORITES);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        MovieSortType.valueOf("asd");
        outState.putBoolean(SAVED_SORT_TYPE, MovieSortType.valueOf(movieSortType));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Log.i(LOG_TAG, "[SelectedMovie] " + clickedItemIndex);
        intent.putExtra("MovieEntity", mAdapter.getMovieEntityList().get(clickedItemIndex));
        startActivity(intent);
    }
}
