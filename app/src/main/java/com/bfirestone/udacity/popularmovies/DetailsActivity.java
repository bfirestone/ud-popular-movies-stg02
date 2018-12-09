package com.bfirestone.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.Utils.AppExecutors;
import com.bfirestone.udacity.popularmovies.adapter.MovieCastAdapter;
import com.bfirestone.udacity.popularmovies.adapter.MovieReviewsAdapter;
import com.bfirestone.udacity.popularmovies.adapter.MovieTrailerAdapter;
import com.bfirestone.udacity.popularmovies.api.MovieApiClient;
import com.bfirestone.udacity.popularmovies.api.model.Cast;
import com.bfirestone.udacity.popularmovies.api.model.Genre;
import com.bfirestone.udacity.popularmovies.api.model.MovieDetailsResponse;
import com.bfirestone.udacity.popularmovies.api.model.Review;
import com.bfirestone.udacity.popularmovies.api.model.Trailer;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.glide.GlideApp;
import com.bfirestone.udacity.popularmovies.service.TheMovieDatabaseApiService;
import com.bfirestone.udacity.popularmovies.view.DetailsActivityViewModel;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Iterator;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @BindString(R.string.extra_name_entity)
    String EXTRA_MOVIE_ENTITY;

    @BindView(R.id.iv_movie_poster_details)
    ImageView moviePoster;

    @BindView(R.id.iv_movie_cover_details)
    ImageView movieCover;

    @BindView(R.id.tv_movie_details_title)
    TextView movieTitle;

    @BindView(R.id.tv_movie_details_language)
    TextView movieLanguage;

    @BindView(R.id.tv_movie_details_overview)
    TextView moviePlot;

    @BindView(R.id.tv_movie_details_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.tv_movie_details_vote_avg)
    TextView movieVoteAverage;

    @BindView(R.id.tv_movie_details_popularity)
    TextView moviePopularity;

    @BindView(R.id.tv_movie_details_genres_list)
    TextView movieGenres;

    @BindView(R.id.ib_movie_fave_button)
    ImageView faveButton;

    @BindView(R.id.rv_trailer)
    RecyclerView rvTrailer;

    @BindView(R.id.tv_trailers_not_available)
    TextView trailersNotAvailable;

    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;

    @BindView(R.id.tv_reviews_not_available)
    TextView reviewsNotAvailable;

    @BindView(R.id.rv_cast)
    RecyclerView rvCast;

    @BindView(R.id.tv_cast_not_available)
    TextView castNotAvailable;

    @BindString(R.string.fave_added)
    String faveAddedText;

    @BindString(R.string.fave_removed)
    String faveRemovedText;

    @BindDrawable(R.drawable.ic_favorite_border_black_18dp)
    Drawable unFavedButtonDrawable;

    @BindDrawable(R.drawable.ic_favorite_black_18dp)
    Drawable favedButtonDrawable;

    @BindString(R.string.TMDB_BASE_IMG_URL)
    String movieDbBaseImageUrl;

    @BindString(R.string.TMDB_API_KEY)
    String apiKey;

    private DetailsActivityViewModel detailsActivityViewModel;
    private MovieEntity selectedMovie;
    private MovieDetailsResponse fullMovieDetails;
    private TheMovieDatabaseApiService movieDatabaseApiService;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private MovieCastAdapter mMovieCastAdapter;
    private MovieReviewsAdapter mMovieReviewsAdapter;
    private boolean isMovieFaved;
    private AppExecutors appExecutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        // Setup Adapters
        mMovieTrailerAdapter = new MovieTrailerAdapter(this);
        mMovieCastAdapter = new MovieCastAdapter(this);
        mMovieReviewsAdapter = new MovieReviewsAdapter(this);

        // setup cast recycler view
        rvCast.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rvCast.setAdapter(mMovieCastAdapter);

        // setup trailer recycler view
        rvTrailer.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(mMovieTrailerAdapter);

        // setup reviews recycler view
        rvReviews.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rvReviews.setAdapter(mMovieReviewsAdapter);

        // Setup Executor (threading)
        appExecutors = AppExecutors.getExecutorInstance();

        Intent intent = getIntent();
        selectedMovie = intent.getParcelableExtra(EXTRA_MOVIE_ENTITY);
        Log.v(LOG_TAG, "ParcelableExtra: " + selectedMovie.toString());

        // setup retrofit cache
        int cacheSize = 40 * 1024 * 1024; // 40 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);

        movieDatabaseApiService = new MovieApiClient()
                .getRetrofitClient(
                        getResources().getString(R.string.TMDB_BASE_API_URL),
                        new OkHttpClient.Builder()
                                .cache(cache)
                                .build())
                .create(TheMovieDatabaseApiService.class);

        detailsActivityViewModel = ViewModelProviders.of(this)
                .get(DetailsActivityViewModel.class);

        setupFaveButton();

        movieTitle.setText(selectedMovie.getTitle());
        movieLanguage.setText(selectedMovie.getOriginalLanguage());
        moviePlot.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(selectedMovie.getFormattedReleaseDate());
        movieVoteAverage.setText(new StringBuilder().append(selectedMovie.getVoteAverage()));
        moviePopularity.setText(new StringBuilder().append(selectedMovie.getPopularity()));

        GlideApp.with(this)
                .load(movieDbBaseImageUrl + selectedMovie.getBackdropPath())
                .placeholder(R.drawable.gradient_background)
                .error(R.drawable.ic_launcher_background)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(moviePoster);

        GlideApp.with(this)
                .load(movieDbBaseImageUrl + selectedMovie.getPosterPath())
                .placeholder(R.drawable.gradient_background)
                .error(R.drawable.ic_launcher_background)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieCover);

        fetchFullMovieDetails();
    }

    private void setupFaveButton() {
        AppExecutors.getExecutorInstance().getDiskIO().execute(() -> {
            isMovieFaved = detailsActivityViewModel.isFave(selectedMovie.getId());
            Log.d(LOG_TAG, "movie: " + selectedMovie.getTitle() + " faved=" + isMovieFaved);
            if (isMovieFaved) {
                runOnUiThread(() -> faveButton.setImageDrawable(favedButtonDrawable));
            } else {
                runOnUiThread(() -> faveButton.setImageDrawable(unFavedButtonDrawable));
            }
        });

        faveButton.setOnClickListener(v -> onFavoriteClick());
    }

    private void generateGenreNames() {
        StringBuilder genreNames = new StringBuilder();

        Iterator<Genre> genreIterator = fullMovieDetails.getGenres().iterator();
        while (genreIterator.hasNext()) {
            Genre genre = genreIterator.next();

            if (!genreIterator.hasNext()) {
                genreNames.append(genre.getName());
            } else {
                genreNames.append(genre.getName()).append(",");
            }
        }

        movieGenres.setText(genreNames);
    }

    private void generateMovieCast() {
        List<Cast> cast =  fullMovieDetails.getCredits().getCast();

        if (cast != null && cast.size() > 0) {
            rvCast.setVisibility(View.VISIBLE);
            castNotAvailable.setVisibility(View.GONE);
            mMovieCastAdapter.setCast(cast);
        } else {
            rvCast.setVisibility(View.GONE);
            castNotAvailable.setVisibility(View.VISIBLE);
        }
    }

    private void generateMovieReviews() {
        List<Review> reviews =  fullMovieDetails.getReviewList().getReviews();

        if (reviews != null && reviews.size() > 0) {
            rvReviews.setVisibility(View.VISIBLE);
            reviewsNotAvailable.setVisibility(View.GONE);
            mMovieReviewsAdapter.setReviews(reviews);

            if (rvReviews.getAdapter() != null) {
                Log.d(LOG_TAG, "[generateMovieReviews] setting up recycler view & visibility :: "
                        + rvReviews.getAdapter().getItemCount());
            }
        } else {
            rvReviews.setVisibility(View.GONE);
            reviewsNotAvailable.setVisibility(View.VISIBLE);
        }
    }

    private void generateMovieTrailers() {
        List<Trailer> trailers = fullMovieDetails.getTrailerList().getTrailers();

        if (trailers != null && trailers.size() > 0) {
            rvTrailer.setVisibility(View.VISIBLE);
            trailersNotAvailable.setVisibility(View.GONE);
            mMovieTrailerAdapter.setTrailers(trailers);
        } else {
            rvTrailer.setVisibility(View.GONE);
            trailersNotAvailable.setVisibility(View.VISIBLE);
        }

    }

    private void fetchFullMovieDetails() {
        Call<MovieDetailsResponse> call = movieDatabaseApiService.getMovieDetails(
                selectedMovie.getId(), apiKey, "videos,reviews,credits");

        Log.v(LOG_TAG, "full movie fetch url: " + call.request().url());
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailsResponse> call,
                                   @NonNull Response<MovieDetailsResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    fullMovieDetails = response.body();
                    Log.v(LOG_TAG, "total_review="
                            + fullMovieDetails.getReviewList().getReviews().size()
                            + " total_trailers=" + fullMovieDetails.getTrailerList().getTrailers().size()
                            + " total_cast=" + fullMovieDetails.getCredits().getCast().size());

                    generateGenreNames();
                    generateMovieTrailers();
                    generateMovieCast();
                    generateMovieReviews();

                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailsResponse> call,
                                  @NonNull Throwable t) {

                Toast.makeText(DetailsActivity.this, "Error Fetching MovieDetails",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onFavoriteClick() {
        String faveString = (isMovieFaved) ? "fave_removed" : "fave_added";
        Log.v(LOG_TAG, String.format("movie_id=%d movie_title=%s fave_status=%s",
                selectedMovie.getId(), selectedMovie.getTitle(), faveString));

        if (isMovieFaved) {
            appExecutors.getDiskIO().execute(() -> detailsActivityViewModel
                    .removeFave(selectedMovie));

            runOnUiThread(() -> {
                faveButton.setImageDrawable(unFavedButtonDrawable);
                Toast.makeText(this, faveRemovedText, Toast.LENGTH_SHORT).show();
            });
            isMovieFaved = false;
        } else {
            appExecutors.getDiskIO().execute(() -> detailsActivityViewModel
                    .addFave(selectedMovie));
            runOnUiThread(() -> {
                faveButton.setImageDrawable(favedButtonDrawable);
                Toast.makeText(this, faveAddedText, Toast.LENGTH_SHORT).show();
            });
            isMovieFaved = true;
        }
    }
}
