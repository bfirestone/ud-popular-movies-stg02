package com.bfirestone.udacity.popularmovies.service;

import com.bfirestone.udacity.popularmovies.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDatabaseService {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey,
                                          @Query("page") int page);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey,
                                         @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Query("api_key") String apiKey,
                                        @Path("id") String id);
}
