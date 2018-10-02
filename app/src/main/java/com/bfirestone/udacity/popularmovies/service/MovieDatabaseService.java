package com.bfirestone.udacity.popularmovies.service;

import com.bfirestone.udacity.popularmovies.models.GenreListResponse;
import com.bfirestone.udacity.popularmovies.models.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDatabaseService {
    @GET("movie/top_rated")
    Call<MovieListResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenreListResponse> getGenreList(@Query("api_key") String apiKey);


    @GET("movie/{id}")
    Call<MovieListResponse> getMovieDetails(@Path("id") int id,
                                            @Query("api_key") String apiKey);

    @GET("genre/{id}")
    Call<GenreListResponse> getGenreDetails(@Path("id") int id,
                                            @Query("api_key") String apiKey);
}
