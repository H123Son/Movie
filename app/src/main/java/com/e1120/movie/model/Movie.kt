package com.e1120.movie.model


data class Movie(
    val vote_count: Int = 0,
    val vote_average: Float = 0F,
    val id: Int = 0,
    val title: String? = null,
    val poster_path: String? = null,
    val original_language: String? = null ,
    val original_title: String? = null ,
    val genre_ids: Array<Int>? = arrayOf(),
    val overview: String? = null,
    val release_date: String? = null
)
