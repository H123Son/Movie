package com.e1120.movie.retrofit;

import android.content.Context;

public class MovieVM {
    private MovieRepository movieRepository;

    public MovieVM(Context context) {
        movieRepository = new MovieRepository(context);
    }

    public void getMovies(String filterType, String apiKey, String language, int page) {
        movieRepository.getMovies(filterType, apiKey, language, page);
    }

    public void getTrailers(int id, String apiKey, String language) {
        movieRepository.getTrailers(id, apiKey, language);
    }

    public void getReviews(int id, String apiKey, String language) {
        movieRepository.getReviews(id, apiKey, language);
    }

    public void getSimilar(int id, String apiKey, String language) {
        movieRepository.getSimilar(id, apiKey, language);
    }
}
