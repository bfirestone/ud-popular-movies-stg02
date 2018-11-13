package com.bfirestone.udacity.popularmovies.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import static com.bfirestone.udacity.popularmovies.AppConstants.DB_TABLE_MOVIES;

@Entity(tableName = DB_TABLE_MOVIES)
public class MovieEntity {

    @PrimaryKey
    private int movieId;
    private int voteCount;
    private boolean video;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private List<Integer> genreIds;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;

    public MovieEntity(int movieId, int voteCount, boolean video, double voteAverage,
                       String title, double popularity, String posterPath, String originalLanguage,
                       String originalTitle, List<Integer> genreIds, String backdropPath,
                       boolean adult, String overview, String releaseDate) {


        this.movieId = movieId;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean getVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
