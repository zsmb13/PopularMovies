package com.popularmovies.vpaliy.data.source.remote;


import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieDatabaseAPI {

    @GET("movie/popular")
    Observable<MovieWrapper> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") String id,
                                      @Query("api_key") String apiKey);

    @GET("movie/{id}/images")
    Observable<BackdropsWrapper> getBackdrops(@Path("id") String id,
                                              @Query("api_key") String apiKey);
    @GET("movie/{id}/reviews")
    Observable<ReviewWrapper> getReviews(@Path("id") String id,
                                         @Query("api_key") String apiKey);

    @GET("movie/{id}/similar")
    Observable<MovieWrapper> getSimilarMovies(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Observable<CastWrapper> getMovieCast(@Path("id") String id,
                                         @Query("api_key") String apiKey);

}
