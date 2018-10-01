package com.bfirestone.udacity.popularmovies;

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

import com.bfirestone.udacity.popularmovies.Utils.NetworkConnectionDetector;
import com.bfirestone.udacity.popularmovies.models.MovieDetails;
import com.bfirestone.udacity.popularmovies.models.MovieResponse;
import com.bfirestone.udacity.popularmovies.service.MovieDatabaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public RecyclerView mMovieList;
    private static String apiKey;
    private static final MovieSortType defaultSort = MovieSortType.MOST_POPULAR;
    public List<MovieDetails> movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = getResources().getString(R.string.TMDB_API_KEY);

        getMovieListBySort(defaultSort);
    }

//    private void getPopularMovies() {
//
//        mMovieList = findViewById(R.id.rv_main);
//
//        NetworkConnectionDetector detector = new NetworkConnectionDetector();
//
//        if (detector.isNetworkAvailable(this)) {
//            retrofit = getRetrofitInstance();
//
//            MovieDatabaseService movieService = retrofit.create(MovieDatabaseService.class);
//
//            Call<MovieResponse> call = movieService.getPopularMovies(apiKey, numPageToFetch);
//
//            Log.i(LOG_TAG, "[popular_movies] movie db api" +
//                    movieService.getPopularMovies(apiKey, numPageToFetch)
//                            .request().url().toString());
//
//            call.enqueue(new Callback<MovieResponse>() {
//                @Override
//                public void onResponse(@NonNull Call<MovieResponse> call,
//                                       @NonNull Response<MovieResponse> response) {
//
//                    if (response.isSuccessful() && response.body() != null) {
//                        movieDetails = response.body().getResults();
//                        generateMovieList(movieDetails);
//                        Log.d(LOG_TAG, "[popular_movies] num fetched=" + movieDetails.size());
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
//                    Toast.makeText(
//                            MainActivity.this, "Error Fetching MovieDetails List",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Log.i(LOG_TAG, "[network] not available");
//        }
//    }
//
//    private void getTopRatedMovies() {
//        NetworkConnectionDetector detector = new NetworkConnectionDetector();
//
//        if (detector.isNetworkAvailable(this)) {
//            retrofit = getRetrofitInstance();
//
//            MovieDatabaseService movieService = retrofit.create(MovieDatabaseService.class);
//
//            Call<MovieResponse> call = movieService.getTopRatedMovies(apiKey, numPageToFetch);
//            Log.i(LOG_TAG, "[top_rated_movies] movie db api" +
//                    movieService.getTopRatedMovies(apiKey, numPageToFetch)
//                    .request().url().toString());
//
//            call.enqueue(new Callback<MovieResponse>() {
//                @Override
//                public void onResponse(@NonNull Call<MovieResponse> call,
//                                       @NonNull Response<MovieResponse> response) {
//
//                    if (response.isSuccessful() && response.body() != null) {
//                        movieDetails = response.body().getResults();
//                        generateMovieList(movieDetails);
//                        Log.d(LOG_TAG, "[top_rated_movies] num fetched=" + movieDetails.size());
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<MovieResponse> call,
//                                      @NonNull Throwable t) {
//
//                    Toast.makeText(
//                            MainActivity.this, "Error Fetching MovieDetails List",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Log.i(LOG_TAG, "[network] not available");
//        }
//    }

    private void getMovieListBySort(MovieSortType movieSortType) {
        mMovieList = findViewById(R.id.rv_main);

        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        if (detector.isNetworkAvailable(this)) {
            int numPageToFetch = 1;

            Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.TMDB_BASE_API_URL))
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            MovieDatabaseService movieService = retrofit.create(MovieDatabaseService.class);

            Call<MovieResponse> call;

            if (movieSortType == MovieSortType.HIGHEST_RATING) {
                setTitle(R.string.sort_highest_rating);
                call = movieService.getTopRatedMovies(apiKey, numPageToFetch);
            } else {
                setTitle(R.string.sort_most_popular);
                call = movieService.getPopularMovies(apiKey, numPageToFetch);
            }

            Log.i(LOG_TAG, "[top_rated_movies] movie db api" +
                    movieService.getTopRatedMovies(apiKey, numPageToFetch)
                            .request().url().toString());

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call,
                                       @NonNull Response<MovieResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        movieDetails = response.body().getResults();
                        Log.i(LOG_TAG, "[movie_details] " + movieDetails);
                        generateMovieList(movieDetails);
                        Log.d(LOG_TAG, "[top_rated_movies] num fetched=" + movieDetails.size());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call,
                                      @NonNull Throwable t) {

                    Toast.makeText(
                            MainActivity.this, "Error Fetching MovieDetails List",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i(LOG_TAG, "[network] not available");
        }
    }

    private void generateMovieList(final List<MovieDetails> results) {
        MovieGridLayoutAdapter.MovieItemClickListener movieItemClickListener = new MovieGridLayoutAdapter.MovieItemClickListener() {
            @Override
            public void onMovieItemClick(int movieItemIndex) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                MovieDetails movie = results.get(movieItemIndex);
                Log.i(LOG_TAG, "[movie] " + movie);
                intent.putExtra("Movie", results.get(movieItemIndex));
                startActivity(intent);
            }
        };

        MovieGridLayoutAdapter gridLayoutAdapter = new MovieGridLayoutAdapter(this,
                results, movieItemClickListener);

        mMovieList.setHasFixedSize(true);
        mMovieList.setLayoutManager(new GridLayoutManager(this, 2));
        mMovieList.setAdapter(gridLayoutAdapter);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
