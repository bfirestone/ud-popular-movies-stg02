package com.bfirestone.udacity.popularmovies.service;

import com.bfirestone.udacity.popularmovies.api.model.MovieDetailsResponse;
import com.bfirestone.udacity.popularmovies.api.model.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabaseApiService {
    @GET("movie/{sort_order}")
    Call<MovieListResponse> getMoviesBySortOrder(@Path("sort_order") String sortOrder,
                                                 @Query("api_key") String apiKey,
                                                 @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int id,
                                               @Query("api_key") String apiKey,
                                               @Query("append_to_response") String appendToResponse);

}
