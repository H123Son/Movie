package com.e1120.movie.retrofit

import android.content.Context
import android.util.Log
import com.e1120.movie.MainActivity
import com.e1120.movie.MovieInfoActivity
import com.e1120.movie.model.Movie
import com.e1120.movie.model.ResponseObject
import com.e1120.movie.model.ReviewResponse
import com.e1120.movie.model.TrailerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository constructor(context: Context) {
    private var mContext: Context
    private val initMovie: IMovie

    private val retrofit = Retrofit.Builder()
        .baseUrl(MainActivity.BASE_MOVIE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    init {
        mContext = context
        initMovie = retrofit.create(IMovie::class.java)
    }

    fun getMovies(filterType: String, apiKey: String, language: String, page: Int) {
        val call = initMovie.getMovies(filterType, apiKey, language, page)
        call.enqueue(object : Callback<ResponseObject> {
            override fun onResponse(
                call: Call<ResponseObject>,
                response: Response<ResponseObject>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val mainActivity = mContext as MainActivity
                    val movies: ArrayList<Movie> = response.body()!!.movies
                    movies.let {
                        mainActivity.movieAdapter.update(it)
                        mainActivity.listMovie = it
                    }
                }
            }

            override fun onFailure(call: Call<ResponseObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getTrailers(id: Int, apiKey: String, language: String) {
        val call = initMovie.getTrailers(id, apiKey, language)
        call.enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val movieInfo = mContext as MovieInfoActivity
                    val listTrailer = response.body()!!.trailers
                    movieInfo.apply {
                        trailerMovies = listTrailer
                        trailerAdapter.update(listTrailer)
                    }
                }

            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getReviews(id: Int, apiKey: String, language: String) {
        Log.i("SON", "onResponse: $id")
        val call = initMovie.getReviews(id, apiKey, language)
        call.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val movieInfo = mContext as MovieInfoActivity
                    val listReviews = response.body()!!.reviews
                    movieInfo.reviews = listReviews
                    movieInfo.reviewAdapter.updateReview(listReviews)
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

            }
        })
    }

    fun getSimilar(id: Int, apiKey: String, language: String) {
        val call = initMovie.getSimilar(id, apiKey, language)
        Log.i("SON", "getSimilar: $id")

        call.enqueue(object : Callback<ResponseObject> {
            override fun onResponse(
                call: Call<ResponseObject>,
                response: Response<ResponseObject>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val listMovies = response.body()?.movies
                    val movieInfor = mContext as MovieInfoActivity
                    listMovies?.let {
                        movieInfor.movies = listMovies
                        movieInfor.movieAdapter.update(listMovies)
                    }

                }
            }

            override fun onFailure(call: Call<ResponseObject>, t: Throwable) {
            }

        })
    }
}