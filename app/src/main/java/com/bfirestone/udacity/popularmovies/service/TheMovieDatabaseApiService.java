package com.bfirestone.udacity.popularmovies.service;

import com.bfirestone.udacity.popularmovies.api.model.response.CreditsResponse;
import com.bfirestone.udacity.popularmovies.api.model.response.MovieDetailsResponse;
import com.bfirestone.udacity.popularmovies.api.model.response.MovieListResponse;
import com.bfirestone.udacity.popularmovies.api.model.response.ReviewsResponse;
import com.bfirestone.udacity.popularmovies.api.model.response.VideosResponse;

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

    // TODO: better way to get the data instead of 3x the http calls
//    @GET("movie/{id}")
//    Call<MovieDetailsResponse> getAllMovieDetails(@Path("id") int id,
//                                                  @Query("api_key") String apiKey,
//                                                  @Query("append_to_response") String appendToResponse);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsResponse> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideosResponse> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<CreditsResponse> getMovieCredits(@Path("id") int id, @Query("api_key") String apiKey);
}
