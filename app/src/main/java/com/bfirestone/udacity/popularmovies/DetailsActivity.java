package com.bfirestone.udacity.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.Utils.AppExecutors;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.bfirestone.udacity.popularmovies.glide.GlideApp;
import com.bfirestone.udacity.popularmovies.view.DetailsActivityViewModel;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    public static final String EXTRA_MOVIE_ENTITY = "MovieEntity";

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

    @BindString(R.string.fave_added)
    String faveAddedText;

    @BindString(R.string.fave_removed)
    String faveRemovedText;

    @BindDrawable(R.drawable.ic_favorite_border_black_18dp)
    Drawable unFavedButtonDrawable;

    @BindDrawable(R.drawable.ic_favorite_black_18dp)
    Drawable favedButtonDrawable;

    private DetailsActivityViewModel viewModel;

    //    private static GenreParcelableSparseArray genreParcelableSparseArray;
    private MovieEntity selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(DetailsActivityViewModel.class);

        Intent intent = getIntent();

        selectedMovie = intent.getParcelableExtra(EXTRA_MOVIE_ENTITY);

        faveButton.setOnClickListener(v -> onFaveButtonClicked());

        // TODO: replace this with a room lookup for genre id -> genre name
//        genreParcelableSparseArray = intent.getParcelableExtra("GenreMap");
//        String genreNameList = getGenresNames(selectedMovie.getGenreIds());

        movieTitle.setText(selectedMovie.getTitle());
        movieLanguage.setText(selectedMovie.getOriginalLanguage());
        moviePlot.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(selectedMovie.getFormattedReleaseDate());
        movieVoteAverage.setText(new StringBuilder().append(selectedMovie.getVoteAverage()));
        moviePopularity.setText(new StringBuilder().append(selectedMovie.getPopularity()));
//        movieGenres.setText(genreNameList);

        Log.i(LOG_TAG, selectedMovie.toString());
        AppExecutors.getExecutorInstance().getDiskIO().execute(() -> {
            boolean isFavorite = viewModel.isFavorite(selectedMovie.getId());
            Log.i(LOG_TAG, "movie: " + selectedMovie.getTitle() + " faved=" + isFavorite);
            if (isFavorite) {
                runOnUiThread(() -> faveButton.setImageDrawable(favedButtonDrawable));
            } else {
                runOnUiThread(() -> faveButton.setImageDrawable(unFavedButtonDrawable));
            }
        });

        // TODO: change this to a single network call to get the /movie/{movie_id} and append the video attr
        // https://api.themoviedb.org/3/movie/353081?api_key=<api_key>&append_to_response=videos,images
        GlideApp.with(this)
                .load(this.getResources().getString(R.string.TMDB_BASE_IMG_URL) + selectedMovie.getBackdropPath())
                .placeholder(R.drawable.gradient_background)
                .error(R.drawable.ic_launcher_background)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(moviePoster);

        GlideApp.with(this)
                .load(this.getResources().getString(R.string.TMDB_BASE_IMG_URL) + selectedMovie.getPosterPath())
                .placeholder(R.drawable.gradient_background)
                .error(R.drawable.ic_launcher_background)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(movieCover);
    }

    private void onFaveButtonClicked() {
        AppExecutors.getExecutorInstance().getDiskIO().execute(() -> {
            boolean isFavorite = viewModel.isFavorite(selectedMovie.getId());
            if (isFavorite) {
                viewModel.removeMovieFave(selectedMovie);
                runOnUiThread(() -> {
                    faveButton.setImageDrawable(unFavedButtonDrawable);
                    Toast.makeText(this, faveRemovedText, Toast.LENGTH_SHORT).show();
                });
            } else {
                viewModel.addMovieFave(selectedMovie);
                runOnUiThread(() -> {
                    faveButton.setImageDrawable(favedButtonDrawable);
                    Toast.makeText(this, faveAddedText, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
