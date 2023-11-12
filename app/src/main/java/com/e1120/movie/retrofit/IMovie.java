package com.e1120.movie.retrofit;

import com.e1120.movie.model.Movie;
import com.e1120.movie.model.ResponseObject;
import com.e1120.movie.model.ReviewResponse;
import com.e1120.movie.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovie {

    @GET("3/movie/{filter_type}")
    Call<ResponseObject> getMovies(@Path("filter_type") String filterType,
                                   @Query("api_key") String apiKey,
                                   @Query("language") String language,
                                   @Query("page") int page);

    @GET("3/movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(@Path("movie_id") int movieId,
                                      @Query("api_key") String apiKey,
                                      @Query("language") String language
    );

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("movie_id") int movieId,
                                    @Query("api_key") String apiKey,
                                    @Query("language") String language
    );

    @GET("3/movie/{movie_id}/similar")
    Call<ResponseObject> getSimilar(@Path("movie_id") int movieId,
                                    @Query("api_key") String apiKey,
                                    @Query("language") String language
    );
}