package com.bfirestone.udacity.popularmovies.service;

import com.bfirestone.udacity.popularmovies.api.models.MovieDetailsResponse;
import com.bfirestone.udacity.popularmovies.api.models.GenreListResponse;
import com.bfirestone.udacity.popularmovies.api.models.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabaseApiService {
    @GET("movie/top_rated")
    Call<MovieListResponse> getTopRatedMovies(@Query("api_key") String apiKey,
                                              @Query("page") int page);

    @GET("movie/popular")
    Call<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey,
                                             @Query("page") int page);

    @GET("genre/movie/list")
    Call<GenreListResponse> getGenreList(@Query("api_key") String apiKey);


    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int id,
                                               @Query("api_key") String apiKey,
                                               @Query("append_to_response") String appendToResponse);

}
