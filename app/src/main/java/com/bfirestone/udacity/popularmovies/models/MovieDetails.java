package com.bfirestone.udacity.popularmovies.models;


import com.squareup.moshi.Json;

import java.util.List;

public class MovieDetails {

    @Json(name = "poster_path")
    private String posterPath;

    @Json(name ="adult")
    private boolean adult;

    @Json(name ="overview")
    private String overview;

    @Json(name ="release_date")
    private String releaseDate;

    @Json(name ="genre_ids")
    private List<Integer> genreIds;

    @Json(name ="id")
    private int id;

    @Json(name ="original_title")
    private String originalTitle;

    @Json(name ="original_language")
    private String originalLanguage;

    @Json(name ="title")
    private String title;

    @Json(name ="backdrop_path")
    private String backdropPath;

    @Json(name ="popularity")
    private double popularity;

    @Json(name ="vote_count")
    private int voteCount;

    @Json(name ="video")
    private boolean video;

    @Json(name ="vote_average")
    private double voteAverage;

    public MovieDetails(String posterPath, boolean adult, String overview, String releaseDate,
                        List<Integer> genreIds, int id, String originalTitle, String originalLanguage,
                        String title, String backdropPath, double popularity, int voteCount, boolean video,
                        double voteAverage) {

        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}

