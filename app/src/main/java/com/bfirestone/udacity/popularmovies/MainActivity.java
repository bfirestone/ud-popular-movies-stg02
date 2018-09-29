package com.bfirestone.udacity.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private static Retrofit retrofit;
    private static String apiKey;
    private int numPageToFetch = 1;
    public List<MovieDetails> movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.sort_most_popular);
        apiKey = getResources().getString(R.string.TMDB_API_KEY);
        getPopularMovies();
    }

    private void getPopularMovies() {

        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        if (detector.isNetworkAvailable(this)) {
            retrofit = getRetrofitInstance();

            MovieDatabaseService movieService = retrofit.create(MovieDatabaseService.class);

            Call<MovieResponse> call = movieService.getPopularMovies(apiKey, numPageToFetch);

            Log.i(LOG_TAG, "[popular_movies] movie db api" +
                    movieService.getPopularMovies(apiKey, numPageToFetch)
                    .request().url().toString());

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call,
                                       @NonNull Response<MovieResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        movieDetails = response.body().getResults();
                        generateMovieList(movieDetails);
                        Log.d(LOG_TAG, "[popular_movies] num fetched=" + movieDetails.size());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Toast.makeText(
                            MainActivity.this, "Error Fetching MovieDetails List",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i(LOG_TAG, "[network] not available");
        }
    }

    private Retrofit getRetrofitInstance() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.TMDB_BASE_API_URL))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    private void getTopRatedMovies() {
        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        if (detector.isNetworkAvailable(this)) {
            retrofit = getRetrofitInstance();

            MovieDatabaseService movieService = retrofit.create(MovieDatabaseService.class);

            Call<MovieResponse> call = movieService.getTopRatedMovies(apiKey, numPageToFetch);
            Log.i(LOG_TAG, "[top_rated_movies] movie db api" +
                    movieService.getTopRatedMovies(apiKey, numPageToFetch)
                    .request().url().toString());

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call,
                                       @NonNull Response<MovieResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        movieDetails = response.body().getResults();
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

    // todo: this shoudl take an arg for sorting by popularity or rating
    private void generateMovieList(final List<MovieDetails> results) {
        // stub - generate a list of movies for a grid view
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
            // todo: don't like the name, would prefer a single method that gets list of movies and I can pass popularity or rating as a param
            getTopRatedMovies();
        } else if (id == R.id.sort_most_popular) {
            getPopularMovies();
        }

        return super.onOptionsItemSelected(item);
    }
}
