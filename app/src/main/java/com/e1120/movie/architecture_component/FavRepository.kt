package com.e1120.movie.architecture_component

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.e1120.movie.MovieInfoActivity
import com.e1120.movie.architecture_component.db.dao.MovieFavDao
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity

class FavRepository(context: Context) {
    private var movieFavDao: MovieFavDao
    private var listMovieLocal: LiveData<List<MovieFavEntity>>

    init {
        movieFavDao = MovieRoomDb.getInstance(context).dao
        listMovieLocal = movieFavDao.getAll()
    }

    fun getListMovie() = listMovieLocal

    fun add(movieFavEntity: MovieFavEntity) {
        Add(movieFavDao).execute(movieFavEntity)
    }

    fun isValid(movieId: Int) {
        Get(movieFavDao).execute(movieId)
    }

    fun remove(id: Int) {
        Remove(movieFavDao).execute(id)
    }


    private class Add(movieFavDao: MovieFavDao) : AsyncTask<MovieFavEntity, Void, Void>() {
        private var movieFavDao: MovieFavDao

        init {
            this.movieFavDao = movieFavDao
        }

        override fun doInBackground(vararg movie: MovieFavEntity?): Void? {
            movieFavDao.add(movie[0]!!)
            return null
        }
    }

    private class Get(movieFavDao: MovieFavDao) : AsyncTask<Int, Void, Void>() {
        private var movieFavDao: MovieFavDao

        init {
            this.movieFavDao = movieFavDao
        }

        override fun doInBackground(vararg integers: Int?): Void? {
            val count = movieFavDao.isValid(integers[0]!!)
            if (count != 0) {
                Log.d("SON ", "count $count")
                MovieInfoActivity.isFavourite = false
            }
            return null
        }
    }

    private class Remove(movieFavDao: MovieFavDao) : AsyncTask<Int, Void, Void>() {
        private var movieFavDao: MovieFavDao

        init {
            this.movieFavDao = movieFavDao
        }

        override fun doInBackground(vararg p0: Int?): Void? {
            movieFavDao.removeMovie(p0[0]!!)
            return null
        }

    }
}