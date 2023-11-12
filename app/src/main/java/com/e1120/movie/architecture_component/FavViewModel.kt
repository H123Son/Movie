package com.e1120.movie.architecture_component

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.e1120.movie.architecture_component.db.entity.MovieFavEntity

class FavViewModel(application: Application) : AndroidViewModel(application) {
    private val favRepository: FavRepository

    init {
        favRepository = FavRepository(application)
    }

    fun getListMovie() = favRepository.getListMovie()

    fun add(movieFavEntity: MovieFavEntity) {
        favRepository.add(movieFavEntity)
    }

    fun isValid(movieId: Int) {
        favRepository.isValid(movieId)
    }

    fun remove(id: Int) {
        favRepository.remove(id)
    }
}