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
import com.bfirestone.udacity.popularmovies.models.GenreListResponse;
import com.bfirestone.udacity.popularmovies.models.GenreParcelableSparseArray;
import com.bfirestone.udacity.popularmovies.models.Movie;
import com.bfirestone.udacity.popularmovies.models.MovieListResponse;
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
    public List<Movie> movieDetails;
    public GenreListResponse genreList;
    private MovieDatabaseService movieDatabaseService;
    public GenreParcelableSparseArray genreParcelableSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = getResources().getString(R.string.TMDB_API_KEY);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.TMDB_BASE_API_URL))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        genreParcelableSparseArray = new GenreParcelableSparseArray();
        movieDatabaseService = retrofit.create(MovieDatabaseService.class);

        getMovieListBySort(defaultSort);
    }


    private void getMovieListBySort(MovieSortType movieSortType) {
        mMovieList = findViewById(R.id.rv_main);

        NetworkConnectionDetector detector = new NetworkConnectionDetector();

        if (detector.isNetworkAvailable(this)) {

            Call<MovieListResponse> call;
            String movieServiceUrl;

            fetchGenreMap();

            if (movieSortType == MovieSortType.FAVORITES) {
                // get data from room
                Log.i(LOG_TAG, "should do something here");
//                movieDetails = response.body().getResults();
//                Log.i(LOG_TAG, "[movie_details] " + movieDetails);
//                generateMovieList(movieDetails);
//                Log.d(LOG_TAG, "[top_rated_movies] num fetched=" + movieDetails.size());
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
                            movieDetails = response.body().getResults();
                            Log.i(LOG_TAG, "[movie_details] " + movieDetails);
                            generateMovieList(movieDetails);
                            Log.d(LOG_TAG, "[top_rated_movies] num fetched=" + movieDetails.size());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieListResponse> call,
                                          @NonNull Throwable t) {

                        Toast.makeText(
                                MainActivity.this, "Error Fetching Movie List",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            Log.i(LOG_TAG, "[network] not available");
        }
    }

    private void fetchGenreMap() {

        Call<GenreListResponse> call = movieDatabaseService.getGenreList(apiKey);

        call.enqueue(new Callback<GenreListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreListResponse> call,
                                   @NonNull Response<GenreListResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    response.body().getGenres().forEach(genre -> genreParcelableSparseArray.add(genre.getId(), genre.getName()));
                    Log.i(LOG_TAG, "[genre_list] " + genreList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenreListResponse> call,
                                  @NonNull Throwable t) {

                Toast.makeText(
                        MainActivity.this, "Error Fetching Movie List",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateMovieList(final List<Movie> results) {
        MovieGridLayoutAdapter.MovieItemClickListener movieItemClickListener = movieItemIndex -> {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            Movie movie = results.get(movieItemIndex);
            Log.i(LOG_TAG, "[movie] " + movie);
            intent.putExtra("Movie", movie);
            intent.putExtra("GenreMap", genreParcelableSparseArray);
            startActivity(intent);
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
