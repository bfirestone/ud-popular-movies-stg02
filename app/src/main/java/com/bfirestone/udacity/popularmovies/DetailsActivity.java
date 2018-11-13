package com.bfirestone.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfirestone.udacity.popularmovies.models.GenreParcelableSparseArray;
import com.bfirestone.udacity.popularmovies.models.Movie;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetailsActivity extends AppCompatActivity {

    ImageView moviePoster;
    TextView movieTitle;
    TextView movieLanguage;
    TextView moviePlot;
    TextView movieReleaseDate;
    TextView movieVoteAverage;
    TextView moviePopularity;
    TextView movieGenres;

    private static GenreParcelableSparseArray genreParcelableSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        moviePoster = findViewById(R.id.iv_movie_poster_details);
        movieTitle = findViewById(R.id.tv_movie_details_title);
        movieLanguage = findViewById(R.id.tv_movie_details_language);
        moviePlot = findViewById(R.id.tv_movie_details_synopsis);
        movieReleaseDate = findViewById(R.id.tv_movie_details_release_date);
        movieVoteAverage = findViewById(R.id.tv_movie_details_vote_avg);
        moviePopularity = findViewById(R.id.tv_movie_details_popularity);
        movieGenres = findViewById(R.id.tv_movie_details_genres);

        Intent intent = getIntent();
        Movie selectedMovie = intent.getParcelableExtra("Movie");
        genreParcelableSparseArray = intent.getParcelableExtra("GenreMap");

        String genreNameList = getGenresNames(selectedMovie.getGenreIds());

        movieTitle.setText(new StringBuilder("Title: ").append(selectedMovie.getTitle()));
        movieLanguage.setText(new StringBuilder("Language: ").append(selectedMovie.getOriginalLanguage()));
        moviePlot.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(new StringBuilder("Release Date: ").append(selectedMovie.getReleaseDate()));
        movieVoteAverage.setText(new StringBuilder("Rating: ").append(selectedMovie.getVoteAverage()));
        moviePopularity.setText(new StringBuilder("Popularity: ").append(selectedMovie.getPopularity()));
        movieGenres.setText(new StringBuilder("Genres: ").append(genreNameList));

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(this.getResources().getString(R.string.TMDB_BASE_IMG_URL) + selectedMovie.getBackdropPath())
                .placeholder((R.drawable.gradient_background))
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);
    }

    private String getGenresNames(List<Integer> movieGenreIds) {
        StringBuilder sb = new StringBuilder();

        movieGenreIds.forEach(genreId-> {
            sb.append(genreParcelableSparseArray.get(genreId));
            sb.append(", ");
        });

        return sb.substring(0, sb.length() - 2);

    }
}
